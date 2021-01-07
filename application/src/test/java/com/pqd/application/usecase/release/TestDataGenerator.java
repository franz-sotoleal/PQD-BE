package com.pqd.application.usecase.release;

import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TestDataGenerator {

    public static SaveReleaseInfo.Request generateSaveReleaseInfoRequest() {
        return SaveReleaseInfo.Request.of(generateReleaseInfoSonarqube(), 1L);
    }

    public static SaveReleaseInfo.Response generateSaveReleaseInfoResponse() {
        return SaveReleaseInfo.Response.of(generateReleaseInfo());
    }

    public static CalculateQualityLevel.Request generateCalculateQualityLevelRequest() {
        return CalculateQualityLevel.Request.of(generateReleaseInfoSonarqube());
    }

    public static CalculateQualityLevel.Response generateCalculateQualityLevelResponse() {
        return CalculateQualityLevel.Response.of(0.83);
    }

    public static CollectAndSaveAllReleaseData.Request generateCollectAndSaveAllReleaseDataRequest() {
        return CollectAndSaveAllReleaseData.Request.of(123L);
    }

    public static CollectAndSaveAllReleaseData.Response generateCollectAndSaveAllReleaseDataResponse() {
        return CollectAndSaveAllReleaseData.Response.of(generateReleaseInfo());
    }

    public static Product generateProduct() {
        return Product.builder()
                      .id(123L)
                      .name("test-product")
                      .sonarqubeInfo(SonarqubeInfo.builder()
                                                  .baseUrl("base-url")
                                                  .componentName("component-name")
                                                  .token("token")
                                                  .id(1234L)
                                                  .build())
                      .build();
    }

    public static ReleaseInfoSonarqube generateReleaseInfoSonarqube() {
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

    public static ReleaseInfo generateReleaseInfo() {
        return ReleaseInfo.builder()
                          .releaseInfoSonarqube(generateReleaseInfoSonarqube())
                          .productId(1L)
                          .qualityLevel(0.83)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 3),
                                                    LocalTime.of(18, 30, 23)))
                          .build();
    }

    public static ReleaseInfo generateReleaseInfo2() {
        return ReleaseInfo.builder()
                          .releaseInfoSonarqube(generateReleaseInfoSonarqube())
                          .productId(1L)
                          .qualityLevel(0.50)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 7),
                                                    LocalTime.of(16, 30, 23)))
                          .build();
    }

}
