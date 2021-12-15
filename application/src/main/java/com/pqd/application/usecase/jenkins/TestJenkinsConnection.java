package com.pqd.application.usecase.jenkins;

import com.pqd.application.domain.connection.ConnectionResponse;
import com.pqd.application.domain.connection.ConnectionResult;
import com.pqd.application.domain.jenkins.JenkinsInfo;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class TestJenkinsConnection {

    private final JenkinsGateway gateway;

    public ConnectionResponse execute(Request request) {
        ConnectionResult connectionResult = gateway.testJenkinsConnection(request.getJenkinsInfo());

        return ConnectionResponse.of(connectionResult);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Request {
        JenkinsInfo jenkinsInfo;
    }
}
