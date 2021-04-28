package com.pqd.application.usecase.release;

import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@UseCase
public class CalculateQualityLevel {

    public Response execute(Request request) {
        int totalCharacteristics = 3;

        ReleaseInfoSonarqube releaseInfoSq = request.getReleaseInfoSonarqube();

        Double sqSecurityRating =
                transformSonarqubeRating(releaseInfoSq.getSecurityRating());
        Double sqReliabilityRating =
                transformSonarqubeRating(releaseInfoSq.getReliabilityRating());
        Double sqMaintainabilityRating =
                transformSonarqubeRating(releaseInfoSq.getMaintainabilityRating());

        Double qualityLevel =
                (sqSecurityRating + sqReliabilityRating + sqMaintainabilityRating) / totalCharacteristics;

        return Response.of(qualityLevel);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Request {
        ReleaseInfoSonarqube releaseInfoSonarqube;
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {
        Double qualityLevel;
    }

    private Double transformSonarqubeRating(Double rating) {
        if (rating == 1.0) {
            return 1.0;
        } else if (rating == 2.0) {
            return 0.75;
        } else if (rating == 3.0) {
            return 0.5;
        } else if (rating == 4.0) {
            return 0.25;
        } else {
            return 0.0;
        }
    }
}
