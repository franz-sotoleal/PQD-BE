package com.pqd.application.usecase.jira;

import com.pqd.application.domain.connection.ConnectionResponse;
import com.pqd.application.domain.connection.ConnectionResult;
import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class TestJiraConnection {

    private final JiraGateway gateway;

    public ConnectionResponse execute(Request request) {
        ConnectionResult connectionResult = gateway.testJiraConnection(request.getJiraInfo());

        return ConnectionResponse.of(connectionResult);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Request {

        JiraInfo jiraInfo;
    }
}
