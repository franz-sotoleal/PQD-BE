package com.pqd.adapters.jira;

import com.pqd.application.domain.jira.JiraInfo;
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

    public List<JiraSprint> getActiveSprints(JiraInfo jiraInfo) {
        ResponseEntity<JiraActiveSprintResponse> response = makeHttpRequest(jiraInfo);

        return Arrays.stream(Objects.requireNonNull(response.getBody()).getActiveSprints())
                     .map(res -> JiraSprint.builder()
                                           .boardId(res.getBoardId())
                                           .end(res.getEnd())
                                           .start(res.getStart())
                                           .goal(res.getGoal())
                                           .name(res.getName())
                                           .build())
                     .collect(Collectors.toList());
    }

    private ResponseEntity<JiraActiveSprintResponse> makeHttpRequest(JiraInfo jiraInfo) {
        String authBase = jiraInfo.getUserEmail() + ":" + jiraInfo.getToken();
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(authBase.getBytes()));

        String uri = String.format("%s/rest/agile/1.0/board/%s/sprint?state=active",
                                   jiraInfo.getBaseUrl(),
                                   jiraInfo.getBoardId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, basicAuth);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JiraActiveSprintResponse> response;

        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, JiraActiveSprintResponse.class);
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
