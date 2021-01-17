package com.pqd.adapters.jira;

import com.pqd.adapters.jira.model.JiraActiveSprintResponse;
import com.pqd.adapters.jira.model.JiraIssueFieldsResponse;
import com.pqd.adapters.jira.model.JiraSprintIssuesResponse;
import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.jira.JiraIssue;
import com.pqd.application.domain.jira.JiraSprint;
import com.pqd.application.usecase.jira.JiraGateway;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JiraRestClient implements JiraGateway {

    private final RestTemplate restTemplate;

    @Override
    public List<JiraSprint> getActiveSprints(JiraInfo jiraInfo) {
        ResponseEntity<JiraActiveSprintResponse> response = requestActiveSprints(jiraInfo);

        return Arrays.stream(Objects.requireNonNull(response.getBody()).getActiveSprints())
                     .map(res -> JiraSprint.builder()
                                           .sprintId(res.getId())
                                           .boardId(res.getBoardId())
                                           .end(res.getEnd())
                                           .start(res.getStart())
                                           .goal(res.getGoal())
                                           .name(res.getName())
                                           .browserUrl(JiraSprint.createBrowserUrl(jiraInfo.getBaseUrl(), res.getId()))
                                           .build())
                     .collect(Collectors.toList());
    }

    @Override
    public List<JiraIssue> getSprintIssues(JiraInfo jiraInfo, Long sprintId) {
        ResponseEntity<JiraSprintIssuesResponse> response = requestSprintIssues(jiraInfo, sprintId);

        return Arrays.stream(Objects.requireNonNull(response.getBody())
                                    .getIssues())
                     .map(res -> JiraIssue.builder()
                                          .issueId(res.getId())
                                          .key(res.getKey())
                                          .browserUrl(JiraIssue.createBrowserUrl(jiraInfo.getBaseUrl(), res.getKey()))
                                          .fields(JiraIssueFieldsResponse.buildJiraIssueFields(res))
                                          .build())
                     .collect(Collectors.toList());
    }


    private ResponseEntity<JiraActiveSprintResponse> requestActiveSprints(JiraInfo jiraInfo) {
        HttpEntity<String> entity = getAuthorizationHttpEntity(jiraInfo);
        String uri = String.format("%s/rest/agile/1.0/board/%s/sprint?state=active",
                                   jiraInfo.getBaseUrl(),
                                   jiraInfo.getBoardId());

        return makeHttpRequest(jiraInfo, entity, uri, JiraActiveSprintResponse.class);
    }

    private ResponseEntity<JiraSprintIssuesResponse> requestSprintIssues(JiraInfo jiraInfo, Long sprintId) {
        HttpEntity<String> entity = getAuthorizationHttpEntity(jiraInfo);
        String uri = String.format("%s/rest/agile/1.0/sprint/%s/issue?maxResults=100",
                                   jiraInfo.getBaseUrl(),
                                   sprintId);

        return makeHttpRequest(jiraInfo, entity, uri, JiraSprintIssuesResponse.class);
    }

    private <T> ResponseEntity<T> makeHttpRequest(JiraInfo jiraInfo,
                                                  HttpEntity<String> entity,
                                                  String uri,
                                                  Class<T> responseType) {
        ResponseEntity<T> response;
        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, responseType);
        } catch (ResourceAccessException e) {
            throw new JiraConnectionRefusedException(String.format("Connection refused for baseurl %s",
                                                                   jiraInfo.getBaseUrl()));
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                throw new JiraRestClientException(
                        "Connection unauthorized, probably invalid Jira API token or user email");
            }
            throw new JiraRestClientException(String.format("%s", exception.getMessage()));
        }
        return response;
    }

    private HttpEntity<String> getAuthorizationHttpEntity(JiraInfo jiraInfo) {
        String authBase = jiraInfo.getUserEmail() + ":" + jiraInfo.getToken();
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(authBase.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, basicAuth);
        return new HttpEntity<>(headers);
    }

    @NoArgsConstructor
    public static class JiraRestClientException extends RuntimeException {
        public JiraRestClientException(String message) {
            super(message);
        }
    }

    @NoArgsConstructor
    public static class JiraConnectionRefusedException extends JiraRestClientException {
        public JiraConnectionRefusedException(String message) {
            super(message);
        }
    }

}
