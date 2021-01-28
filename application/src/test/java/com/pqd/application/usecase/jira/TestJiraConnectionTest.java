package com.pqd.application.usecase.jira;

import com.pqd.application.domain.connection.ConnectionResult;
import com.pqd.application.domain.jira.JiraInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestJiraConnectionTest {

    private JiraGateway gateway;
    private TestJiraConnection testJiraConnection;

    @BeforeEach
    void setup() {
        gateway = mock(JiraGateway.class);
        testJiraConnection = new TestJiraConnection(gateway);
    }

    @Test
    void GIVEN_request_WHEN_request_executed_THEN_response_returned() {
        ConnectionResult connectionResult = TestDataGenerator.generateConnectionResult();
        JiraInfo jiraInfo = TestDataGenerator.generateJiraInfo();
        when(gateway.testJiraConnection(any())).thenReturn(connectionResult);

        ConnectionResult actual =
                testJiraConnection.execute(TestJiraConnection.Request.of(jiraInfo)).getConnectionResult();
        assertThat(actual).isEqualTo(connectionResult);
    }
}
