package com.pqd.application.usecase.release;

import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.sonarqube.SonarqubeReleaseInfo;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@UseCase
@Transactional
public class SaveReleaseInfo {

    private final ReleaseInfoGateway gateway;
    private final CalculateQualityLevel calculateQualityLevel;

    public Response execute(Request request) {
        Double qualityLevel =
                calculateQualityLevel.execute(CalculateQualityLevel.Request.of(request.getSonarqubeReleaseInfo()))
                                     .getQualityLevel();
        ReleaseInfo releaseInfo = ReleaseInfo.builder()
                                             .created(LocalDateTime.now())
                                             .releaseInfoSonarqubeId(request.getSonarqubeReleaseInfo().getId())
                                             .productId(1L) //TODO productId must come somewhere
                                             .qualityLevel(qualityLevel)
                                             .build();

        ReleaseInfo savedReleaseInfo = gateway.save(releaseInfo);
        return Response.of(savedReleaseInfo);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Request {
        SonarqubeReleaseInfo sonarqubeReleaseInfo;

    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response {
        ReleaseInfo releaseInfo;

    }
}
