package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.sonarqube.SonarqubeReleaseInfo;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class SaveSonarqubeData {

    private final SonarqubeReleaseInfoGateway gateway;

    public Response execute(Request request) {
        SonarqubeReleaseInfo savedRequest = gateway.save(request.getSonarqubeReleaseInfo());
        return Response.of(savedRequest);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Request {
        SonarqubeReleaseInfo sonarqubeReleaseInfo;

    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response {
        SonarqubeReleaseInfo sonarqubeReleaseInfo;

    }
}
