package com.pqd.application.usecase.jira;

import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.jira.JiraIssue;
import com.pqd.application.domain.jira.JiraSprint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RetrieveReleaseInfoJiraTest {

    private JiraGateway gateway;
    private RetrieveReleaseInfoJira retrieveReleaseInfoJira;

    @BeforeEach
    void setup() {
        gateway = mock(JiraGateway.class);
        retrieveReleaseInfoJira = new RetrieveReleaseInfoJira(gateway);
    }

    @Test
    void GIVEN_jira_release_info_exists_WHEN_request_executed_THEN_release_info_jira_returned() {
        JiraSprint jiraSprint = TestDataGenerator.generateJiraSprint_withoutIssues();
        JiraIssue jiraIssue = TestDataGenerator.generateJiraIssue();
        JiraInfo jiraInfo = TestDataGenerator.generateJiraInfo();
        JiraSprint expected = TestDataGenerator.generateJiraSprint();
        when(gateway.getActiveSprints(any())).thenReturn(List.of(jiraSprint));
        when(gateway.getSprintIssues(any(), any())).thenReturn(List.of(jiraIssue));

        List<JiraSprint> response =
                retrieveReleaseInfoJira.execute(RetrieveReleaseInfoJira.Request.of(jiraInfo)).getActiveSprints();

        verify(gateway).getActiveSprints(jiraInfo);
        verify(gateway).getSprintIssues(jiraInfo, jiraSprint.getSprintId());
        assertThat(response.get(0)).isEqualTo(expected);
    }

    @Test
    void GIVEN_jira_release_info_exists_without_issues_WHEN_request_executed_THEN_release_info_jira_returned() {
        JiraSprint jiraSprint = TestDataGenerator.generateJiraSprint_withoutIssues();
        JiraInfo jiraInfo = TestDataGenerator.generateJiraInfo();
        when(gateway.getActiveSprints(any())).thenReturn(List.of(jiraSprint));
        when(gateway.getSprintIssues(any(), any())).thenReturn(List.of());

        List<JiraSprint> response =
                retrieveReleaseInfoJira.execute(RetrieveReleaseInfoJira.Request.of(jiraInfo)).getActiveSprints();

        verify(gateway).getActiveSprints(jiraInfo);
        verify(gateway).getSprintIssues(jiraInfo, jiraSprint.getSprintId());
        assertThat(response.get(0)).isEqualTo(jiraSprint);
    }
}
