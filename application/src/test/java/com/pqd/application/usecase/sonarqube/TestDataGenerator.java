package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.domain.sonarqube.SonarqubeConnectionResult;

public class TestDataGenerator {

    public static ReleaseInfoSonarqube generateReleaseInfoSonarqube() {
        return ReleaseInfoSonarqube.builder()
                                   .id(123L)
                                   .maintainabilityDebt(2.0)
                                   .maintainabilitySmells(34.0)
                                   .maintainabilityRating(2.0)
                                   .reliabilityBugs(10.0)
                                   .reliabilityRating(1.0)
                                   .securityRating(3.0)
                                   .securityVulnerabilities(8.0)
                                   .build();
    }

    public static SonarqubeConnectionResult generateSonarqubeConnectionResult() {
        return SonarqubeConnectionResult.builder()
                                        .connectionOk(true)
                                        .message("One test is enough")
                                        .build();
    }
}
