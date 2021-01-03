package com.pqd.application.usecase.release;

import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@UseCase
public class CalculateQualityLevel {

    public Response execute(Request request) {
        int totalCharacteristics = 3;

        Double sqSecurityRating = transformSonarqubeRating(request.getReleaseInfoSonarqube().getSecurityRating());
        Double sqReliabilityRating = transformSonarqubeRating(request.getReleaseInfoSonarqube().getReliabilityRating());
        Double sqMaintainabilityRating = transformSonarqubeRating(request.getReleaseInfoSonarqube().getMaintainabilityRating());

        Double qualityLevel = (sqSecurityRating / totalCharacteristics)
                              + (sqReliabilityRating / totalCharacteristics)
                              + (sqMaintainabilityRating / totalCharacteristics);

        return Response.of(qualityLevel);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Request {
        ReleaseInfoSonarqube releaseInfoSonarqube;

    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response {
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
