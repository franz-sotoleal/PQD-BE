package com.pqd.adapters.jira;

import com.pqd.adapters.jira.model.JiraActiveSprintResponse;
import com.pqd.adapters.jira.model.JiraSprintIssuesResponse;
import com.pqd.application.domain.connection.ConnectionResult;
import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.jira.JiraIssue;
import com.pqd.application.domain.jira.JiraSprint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JiraRestClientTest {

    private RestTemplate restTemplate;
    private JiraRestClient restClient;

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);
        restClient = new JiraRestClient(restTemplate);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_all_good_WHEN_jira_active_sprints_requested_THEN_active_sprints_returned() {
        JiraActiveSprintResponse jiraActiveSprintResponse = TestDataGenerator.generateActiveSprintsResponse();
        List<JiraSprint> expected = TestDataGenerator.generateActiveSprintList();
        JiraInfo jiraInfo = TestDataGenerator.generateJiraInfo();
        ResponseEntity<JiraActiveSprintResponse> responseEntity =
                new ResponseEntity<>(jiraActiveSprintResponse, HttpStatus.OK);
        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                                   any(HttpMethod.class),
                                   any(),
                                   ArgumentMatchers.<Class<JiraActiveSprintResponse>>any()))
                .thenReturn(responseEntity);

        List<JiraSprint> actual = restClient.getActiveSprints(jiraInfo);

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void GIVEN_all_good_WHEN_jira_sprint_issues_requested_THEN_active_sprints_returned() {
        JiraSprintIssuesResponse jiraSprintIssuesResponse = TestDataGenerator.generateSprintIssuesResponse();
        List<JiraIssue> expected = TestDataGenerator.generateSprintIssuesList();
        JiraInfo jiraInfo = TestDataGenerator.generateJiraInfo();
        ResponseEntity<JiraSprintIssuesResponse> responseEntity =
                new ResponseEntity<>(jiraSprintIssuesResponse, HttpStatus.OK);
        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                                   any(HttpMethod.class),
                                   any(),
                                   ArgumentMatchers.<Class<JiraSprintIssuesResponse>>any()))
                .thenReturn(responseEntity);

        List<JiraIssue> actual = restClient.getSprintIssues(jiraInfo, 1L);

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void GIVEN_request_error_WHEN_jira_active_sprints_requested_THEN_jira_rest_client_exception_thrown() {
        JiraInfo jiraInfo = TestDataGenerator.generateJiraInfo();
        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                                   any(HttpMethod.class),
                                   any(),
                                   ArgumentMatchers.<Class<JiraActiveSprintResponse>>any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "request error"));

        Exception exception =
                assertThrows(Exception.class, () -> restClient.getActiveSprints(jiraInfo));
        assertThat(exception).hasStackTraceContaining("JiraRestClientException");
    }

    @Test
    void GIVEN_request_error_WHEN_jira_sprint_issues_requested_THEN_jira_rest_client_exception_thrown() {
        JiraInfo jiraInfo = TestDataGenerator.generateJiraInfo();
        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                                   any(HttpMethod.class),
                                   any(),
                                   ArgumentMatchers.<Class<JiraActiveSprintResponse>>any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "request error"));

        Exception exception =
                assertThrows(Exception.class, () -> restClient.getSprintIssues(jiraInfo, 1L));
        assertThat(exception).hasStackTraceContaining("JiraRestClientException");
    }

    @Test
    void GIVEN_all_good_WHEN_jira_connection_tested_THEN_jira_connection_result_returned() {
        ConnectionResult connectionResult = TestDataGenerator.generateConnectionResult_success();
        JiraInfo jiraInfo = TestDataGenerator.generateJiraInfo();
        JiraActiveSprintResponse jiraActiveSprintResponse = TestDataGenerator.generateActiveSprintsResponse();
        ResponseEntity<JiraActiveSprintResponse> responseEntity =
                new ResponseEntity<>(jiraActiveSprintResponse, HttpStatus.OK);
        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                                   any(HttpMethod.class),
                                   any(),
                                   ArgumentMatchers.<Class<JiraActiveSprintResponse>>any()))
                .thenReturn(responseEntity);

        ConnectionResult actual = restClient.testJiraConnection(jiraInfo);

        assertThat(actual).isEqualTo(connectionResult);
    }

    @Test
    void GIVEN_invalid_baseurl_WHEN_jira_connection_tested_THEN_corresponding_result_returned() {
        ConnectionResult connectionResult = TestDataGenerator.generateConnectionResult_wrongBaseUrl();
        JiraInfo jiraInfo = TestDataGenerator.generateJiraInfo();
        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                                   any(HttpMethod.class),
                                   any(),
                                   ArgumentMatchers.<Class<JiraActiveSprintResponse>>any()))
                .thenThrow(new ResourceAccessException("", new IOException()));

        ConnectionResult actual = restClient.testJiraConnection(jiraInfo);

        assertThat(actual).isEqualTo(connectionResult);
    }

    @Test
    void GIVEN_unauthorized_request_WHEN_jira_connection_tested_THEN_corresponding_result_returned() {
        ConnectionResult connectionResult = TestDataGenerator.generateConnectionResult_wrongToken();
        JiraInfo jiraInfo = TestDataGenerator.generateJiraInfo();
        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                                   any(HttpMethod.class),
                                   any(),
                                   ArgumentMatchers.<Class<JiraActiveSprintResponse>>any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        ConnectionResult actual = restClient.testJiraConnection(jiraInfo);

        assertThat(actual).isEqualTo(connectionResult);
    }

    @Test
    void GIVEN_invalid_board_WHEN_jira_connection_tested_THEN_corresponding_result_returned() {
        ConnectionResult connectionResult = TestDataGenerator.generateConnectionResult_wrongBoard();
        JiraInfo jiraInfo = TestDataGenerator.generateJiraInfo();
        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                                   any(HttpMethod.class),
                                   any(),
                                   ArgumentMatchers.<Class<JiraActiveSprintResponse>>any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        ConnectionResult actual = restClient.testJiraConnection(jiraInfo);

        assertThat(actual).isEqualTo(connectionResult);
    }

}
