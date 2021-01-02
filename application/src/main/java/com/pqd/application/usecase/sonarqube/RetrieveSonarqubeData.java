package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.sonarqube.SonarqubeReleaseInfo;
import com.pqd.application.usecase.UseCase;
import com.pqd.application.usecase.release.SaveReleaseInfo;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class RetrieveSonarqubeData {

    private final SonarqubeGateway sonarqubeGateway;

    private final SaveSonarqubeData saveSonarqubeData;
    private final SaveReleaseInfo saveReleaseInfo;

    public Response execute(Request request) {

        SonarqubeReleaseInfo sonarqubeReleaseInfo = sonarqubeGateway.getSonarqubeReleaseInfo(request.getBaseUrl(),
                                                                                             request.getComponentName(),
                                                                                             request.getToken());

        //TODO remove saving from here
        SonarqubeReleaseInfo savedSonarqubeReleaseInfo =
                saveSonarqubeData.execute(SaveSonarqubeData.Request.of(sonarqubeReleaseInfo)).getSonarqubeReleaseInfo();
        saveReleaseInfo.execute(SaveReleaseInfo.Request.of(savedSonarqubeReleaseInfo));

        return Response.of(savedSonarqubeReleaseInfo);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response {

        SonarqubeReleaseInfo releaseInfo;
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Request {

        String baseUrl;
        String componentName;
        String token;
    }

}
