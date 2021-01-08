package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.sonarqube.SonarqubeConnectionResult;
import com.pqd.application.usecase.AbstractResponse;
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

    public Response execute(Request request) {
        SonarqubeConnectionResult connectionResult =
                gateway.testSonarqubeConnection(request.getBaseUrl(), request.getComponentName(), request.getToken());

        return Response.of(connectionResult);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {

        SonarqubeConnectionResult connectionResult;
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Request {

        String baseUrl;
        String componentName;
        String token;
    }

}
