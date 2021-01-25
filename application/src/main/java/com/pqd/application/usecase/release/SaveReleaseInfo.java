package com.pqd.application.usecase.release;

import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.release.ReleaseInfoJira;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class SaveReleaseInfo {

    private final ReleaseInfoGateway gateway;
    private final CalculateQualityLevel calculateQualityLevel;

    public Response execute(Request request) {
        Double qualityLevel =
                calculateQualityLevel.execute(CalculateQualityLevel.Request.of(request.getReleaseInfoSonarqube()))
                                     .getQualityLevel();
        ReleaseInfo releaseInfo = ReleaseInfo.builder()
                                             .created(LocalDateTime.now())
                                             .releaseInfoSonarqube(Optional.of(request.getReleaseInfoSonarqube()))
                                             .releaseInfoJira(Optional.of(request.getReleaseInfoJira()))
                                             .productId(request.getProductId())
                                             .qualityLevel(qualityLevel)
                                             .build();

        ReleaseInfo savedReleaseInfo = gateway.save(releaseInfo);
        return Response.of(savedReleaseInfo);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Request {
        ReleaseInfoSonarqube releaseInfoSonarqube;
        ReleaseInfoJira releaseInfoJira;
        Long productId;
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {
        ReleaseInfo releaseInfo;

    }
}
