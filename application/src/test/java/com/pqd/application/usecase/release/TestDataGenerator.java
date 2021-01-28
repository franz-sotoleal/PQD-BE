package com.pqd.application.usecase.release;

import com.pqd.application.domain.jira.*;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.release.ReleaseInfoJira;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class TestDataGenerator {

    public static SaveReleaseInfo.Request generateSaveReleaseInfoRequest() {
        return SaveReleaseInfo.Request.of(generateReleaseInfoSonarqube(), generateReleaseInfoJira(), 1L);
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
                      .sonarqubeInfo(Optional.of(generateSonarqubeInfo()))
                      .jiraInfo(Optional.of(generateJiraInfo()))
                      .build();
    }

    private static SonarqubeInfo generateSonarqubeInfo() {
        return SonarqubeInfo.builder()
                            .baseUrl("baseurl")
                            .componentName("component-name")
                            .token("token")
                            .id(1234L)
                            .build();
    }

    private static JiraInfo generateJiraInfo() {
        return JiraInfo.builder()
                       .userEmail("user@mail.com")
                       .boardId(1L)
                       .id(102L)
                       .token("token123")
                       .baseUrl("https://pqdunittest.atlassian.net")
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
                          .releaseInfoSonarqube(Optional.of(generateReleaseInfoSonarqube()))
                          .releaseInfoJira(Optional.of(generateReleaseInfoJira()))
                          .productId(1L)
                          .qualityLevel(0.83)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 3),
                                                    LocalTime.of(18, 30, 23)))
                          .build();
    }

    public static ReleaseInfo generateReleaseInfo2() {
        return ReleaseInfo.builder()
                          .releaseInfoSonarqube(Optional.of(generateReleaseInfoSonarqube()))
                          .productId(1L)
                          .qualityLevel(0.50)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 7),
                                                    LocalTime.of(16, 30, 23)))
                          .build();
    }

    public static ReleaseInfoJira generateReleaseInfoJira() {
        return ReleaseInfoJira.builder()
                              .jiraSprints(List.of(generateJiraSprint()))
                              .build();
    }

    private static JiraSprint generateJiraSprint() {
        return JiraSprint.builder()
                         .id(null)
                         .sprintId(4L)
                         .name("sprindi nimi 2")
                         .goal("goal 2")
                         .boardId(1L)
                         .start(LocalDateTime.of(LocalDate.of(2021, 1, 16), LocalTime.of(17, 31, 9, 879)))
                         .end(LocalDateTime.of(LocalDate.of(2021, 1, 27), LocalTime.of(14, 30, 9, 879)))
                         .browserUrl("https://pqdunittest.atlassian.net/issues/?jql=Sprint%3D4")
                         .issues(List.of(generateJiraIssue()))
                         .build();
    }

    private static JiraIssue generateJiraIssue() {
        return JiraIssue.builder()
                        .issueId(1001L)
                        .key("PT-1")
                        .browserUrl("https://pqdunittest.atlassian.net/browse/PT-1")
                        .id(null)
                        .fields(JiraIssueFields.builder()
                                               .issueType(JiraIssueType.builder()
                                                                       .description("issue description")
                                                                       .issueId(1001L)
                                                                       .iconUrl(
                                                                               "https://pqdunittest.atlassian.net/blablabla/icon")
                                                                       .name("issue name")
                                                                       .build())
                                               .build())
                        .build();
    }

}
