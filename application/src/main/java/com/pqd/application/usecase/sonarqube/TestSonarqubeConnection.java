package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.connection.ConnectionResponse;
import com.pqd.application.domain.connection.ConnectionResult;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class TestSonarqubeConnection {

    private final SonarqubeGateway gateway;

    public ConnectionResponse execute(Request request) {
        ConnectionResult connectionResult =
                gateway.testSonarqubeConnection(request.getSonarqubeInfo());

        return ConnectionResponse.of(connectionResult);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Request {

        SonarqubeInfo sonarqubeInfo;
    }

}
