package com.pqd.adapters.persistence.release;

import com.pqd.adapters.persistence.release.sonarqube.ReleaseInfoSonarqubeEntity;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TestDataGenerator {

    public static ReleaseInfoEntity generateReleaseInfoEntity() {
        return ReleaseInfoEntity.builder()
                                .sonarqubeReleaseInfoEntity(generateReleaseInfoSonarqubeEntity())
                                .productId(1L)
                                .qualityLevel(0.83)
                                .created(LocalDateTime.of(LocalDate.of(2021, 1, 3),
                                                          LocalTime.of(18, 30, 23)))
                                .build();
    }

    public static ReleaseInfo generateReleaseInfo() {
        return ReleaseInfo.builder()
                          .releaseInfoSonarqube(generateReleaseInfoSonarqube())
                          .productId(1L)
                          .qualityLevel(0.83)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 3),
                                                    LocalTime.of(18, 30, 23)))
                          .build();
    }

    public static ReleaseInfoEntity generateReleaseInfoEntity2() {
        return ReleaseInfoEntity.builder()
                                .sonarqubeReleaseInfoEntity(generateReleaseInfoSonarqubeEntity())
                                .productId(1L)
                                .qualityLevel(0.5)
                                .created(LocalDateTime.of(LocalDate.of(2021, 1, 7),
                                                          LocalTime.of(16, 30, 23)))
                                .build();
    }

    public static ReleaseInfo generateReleaseInfo2() {
        return ReleaseInfo.builder()
                          .releaseInfoSonarqube(generateReleaseInfoSonarqube())
                          .productId(1L)
                          .qualityLevel(0.5)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 7),
                                                    LocalTime.of(16, 30, 23)))
                          .build();
    }

    private static ReleaseInfoSonarqube generateReleaseInfoSonarqube() {
        return ReleaseInfoSonarqube.builder()
                                   .securityRating(1.0)
                                   .securityVulnerabilities(2.0)
                                   .reliabilityRating(3.0)
                                   .reliabilityBugs(4.0)
                                   .maintainabilityRating(5.0)
                                   .maintainabilityDebt(6.0)
                                   .maintainabilitySmells(7.0)
                                   .build();
    }

    private static ReleaseInfoSonarqubeEntity generateReleaseInfoSonarqubeEntity() {
        return ReleaseInfoSonarqubeEntity.builder()
                                         .securityRating(1.0)
                                         .securityVulnerabilities(2.0)
                                         .reliabilityRating(3.0)
                                         .reliabilityBugs(4.0)
                                         .maintainabilityRating(5.0)
                                         .maintainabilityDebt(6.0)
                                         .maintainabilitySmells(7.0)
                                         .build();
    }
}
