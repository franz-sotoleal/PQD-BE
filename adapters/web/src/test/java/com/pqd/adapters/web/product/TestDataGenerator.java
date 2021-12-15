package com.pqd.adapters.web.product;

import com.pqd.adapters.web.product.json.info.SaveProductRequestJson;
import com.pqd.adapters.web.product.json.info.UpdateProductRequestJson;
import com.pqd.adapters.web.product.json.info.jenkins.JenkinsInfoRequestJson;
import com.pqd.adapters.web.product.json.info.jira.JiraInfoRequestJson;
import com.pqd.adapters.web.product.json.info.sonarqube.SonarqubeInfoRequestJson;
import com.pqd.adapters.web.security.jwt.JwtUserProductClaim;
import com.pqd.application.domain.claim.ClaimLevel;
import com.pqd.application.domain.claim.UserProductClaim;
import com.pqd.application.domain.connection.ConnectionResult;
import com.pqd.application.domain.jira.*;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.release.ReleaseInfoJira;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import com.pqd.application.usecase.claim.SaveClaim;
import com.pqd.application.usecase.product.SaveProduct;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestDataGenerator {

    public static ConnectionResult generateConnectionResult() {
        return ConnectionResult.builder()
                               .message("ok")
                               .connectionOk(true)
                               .build();
    }

    public static ReleaseInfo generateReleaseInfo() {
        return ReleaseInfo.builder()
                .releaseInfoSonarqube(Optional.of(generateReleaseInfoSonarqube()))
                .productId(1L)
                .qualityLevel(0.83)
                .created(LocalDateTime.of(LocalDate.of(2021, 1, 3),
                        LocalTime.of(18, 30, 23)))
                .releaseInfoJira(Optional.of(generateReleaseInfoJira()))
                .releaseInfoJenkins(Optional.empty())
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

    private static ReleaseInfoJira generateReleaseInfoJira() {
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

    public static ReleaseInfo generateReleaseInfo2() {
        return ReleaseInfo.builder()
                .releaseInfoSonarqube(Optional.of(generateReleaseInfoSonarqube()))
                .releaseInfoJira(Optional.empty())
                .releaseInfoJenkins(Optional.empty())
                          .productId(1L)
                          .qualityLevel(0.5)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 7),
                                                    LocalTime.of(16, 30, 23)))
                          .build();
    }

    public static List<JwtUserProductClaim> generateProductClaimsFromToken() {
        return List.of(1L, 2L)
                   .stream()
                   .map(id -> JwtUserProductClaim.builder()
                                                 .productId(id)
                                                 .claimLevel(
                                                         ClaimLevel
                                                                 .builder()
                                                                 .value(ClaimLevel.ADMIN)
                                                                 .build())
                                                 .build()).collect(Collectors.toList());
    }

    public static List<Product> generateProductList() {
        return List.of(1L, 2L)
                   .stream()
                   .map(id -> Product.builder()
                           .id(id)
                           .token("product-token" + id)
                           .name("product-name" + id)
                           .sonarqubeInfo(Optional.of(generateSonarqubeInfo()))
                           .jiraInfo(Optional.empty())
                           .jenkinsInfo(Optional.empty())
                                     .build()).collect(Collectors.toList());
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .id(1L)
                                                        .name("Before update")
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson())
                                                        .build())
                                       .build();
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson_missingBaseurl() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .id(1L)
                                                        .name("Before update")
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson_invalid())
                                                        .build())
                                       .build();
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson_missingName() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .id(1L)
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson_invalid())
                                                        .build())
                                       .build();
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson_emptyName() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .name("")
                                                        .id(1L)
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson_invalid())
                                                        .build())
                                       .build();
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson_emptyBaseurl() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .id(1L)
                                                        .name("Before update")
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson_invalid2())
                                                        .build())
                                       .build();
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson_missingComponentName() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .id(1L)
                                                        .name("Before update")
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson_invalid3())
                                                        .build())
                                       .build();
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson_emptyComponentName() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .id(1L)
                                                        .name("Before update")
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson_invalid4())
                                                        .build())
                                       .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson() {
        return SaveProductRequestJson.builder()
                .name("test12")
                .userId(123L)
                .sonarqubeInfo(Optional.of(generateSonarqubeInfoRequestJson()))
                .jiraInfo(Optional.empty())
                .jenkinsInfo(Optional.empty())
                                     .build();
    }

    public static SaveProduct.Response generateSaveProductResponse() {
        return SaveProduct.Response.of(generateProduct());
    }

    public static Product generateProduct() {
        return Product.builder()
                .id(12367L)
                .token("product-token")
                .name("product-name")
                .sonarqubeInfo(Optional.of(generateSonarqubeInfo()))
                .jiraInfo(Optional.of(generateJiraInfo()))
                .jenkinsInfo(Optional.empty())
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

    public static JiraInfo generateJiraInfo() {
        return JiraInfo.builder()
                       .userEmail("user@mail.com")
                       .boardId(1L)
                       .id(102L)
                       .token("token123")
                       .baseUrl("https://pqdunittest.atlassian.net")
                       .build();
    }

    public static JiraInfoRequestJson generateJiraInfoRequestJson() {
        return JiraInfoRequestJson.builder()
                                  .userEmail("user@mail.com")
                                  .boardId(1L)
                                  .token("token123")
                                  .baseUrl("https://pqdunittest.atlassian.net")
                                  .build();
    }

    public static JiraInfoRequestJson generateJiraInfoRequestJson_missingBaseUrl() {
        return JiraInfoRequestJson.builder()
                                  .userEmail("user@mail.com")
                                  .boardId(1L)
                                  .token("token123")
                                  .baseUrl("")
                                  .build();
    }

    public static JiraInfoRequestJson generateJiraInfoRequestJson_missingBoardId() {
        return JiraInfoRequestJson.builder()
                                  .userEmail("user@mail.com")
                                  .token("token123")
                                  .baseUrl("https://pqdunittest.atlassian.net")
                                  .build();
    }

    public static JiraInfoRequestJson generateJiraInfoRequestJson_missingUserEmail() {
        return JiraInfoRequestJson.builder()
                                  .userEmail("")
                                  .boardId(1L)
                                  .token("token123")
                                  .baseUrl("https://pqdunittest.atlassian.net")
                                  .build();
    }

    public static SaveClaim.Response generateSaveClaimResponse() {
        return SaveClaim.Response.of(UserProductClaim.builder()
                                                     .claimLevel(ClaimLevel.builder().value(ClaimLevel.ADMIN).build())
                                                     .productId(12367L)
                                                     .userId(123L)
                                                     .build());
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withNoSqInfo() {
        return SaveProductRequestJson.builder()
                                     .sonarqubeInfo(Optional.empty())
                                     .jiraInfo(Optional.empty())
                                     .name("test12")
                                     .userId(123L)
                                     .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withNoUserId() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .sonarqubeInfo(Optional.of(generateSonarqubeInfoRequestJson()))
                                     .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withInvalidSqInfo() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .userId(123L)
                                     .sonarqubeInfo(Optional.of(generateSonarqubeInfoRequestJson_invalid()))
                                     .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withInvalidSqInfo2() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .userId(123L)
                                     .sonarqubeInfo(Optional.of(generateSonarqubeInfoRequestJson_invalid2()))
                                     .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withInvalidSqInfo3() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .userId(123L)
                                     .sonarqubeInfo(Optional.of(generateSonarqubeInfoRequestJson_invalid3()))
                                     .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withInvalidSqInfo4() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .userId(123L)
                                     .sonarqubeInfo(Optional.of(generateSonarqubeInfoRequestJson_invalid4()))
                                     .build();
    }

    private static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson() {
        return SonarqubeInfoRequestJson.builder()
                                       .baseUrl("base-url")
                                       .componentName("comp-name")
                                       .token("tokenabc123")
                                       .build();
    }

    private static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson_invalid() {
        return SonarqubeInfoRequestJson.builder()
                                       .componentName("comp-name")
                                       .token("tokenabc123")
                                       .build();
    }

    private static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson_invalid2() {
        return SonarqubeInfoRequestJson.builder()
                                       .baseUrl("")
                                       .componentName("comp-name")
                                       .token("tokenabc123")
                                       .build();
    }

    private static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson_invalid3() {
        return SonarqubeInfoRequestJson.builder()
                .baseUrl("base-url")
                .token("tokenabc123")
                .build();
    }

    private static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson_invalid4() {
        return SonarqubeInfoRequestJson.builder()
                .baseUrl("base-url")
                .componentName("")
                .token("tokenabc123")
                .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withInvalidJenkinsInfo() {
        return SaveProductRequestJson.builder()
                .name("test12")
                .userId(123L)
                .jenkinsInfo(Optional.of(JenkinsInfoRequestJson.builder().build()))
                .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withInvalidJenkinsInfoBaseUrl() {
        return SaveProductRequestJson.builder()
                .name("test12")
                .userId(123L)
                .jenkinsInfo(Optional.of(JenkinsInfoRequestJson.builder().username("valid_user").token("validToken").build()))
                .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withInvalidJenkinsInfoUsername() {
        return SaveProductRequestJson.builder()
                .name("test12")
                .userId(123L)
                .jenkinsInfo(Optional.of(JenkinsInfoRequestJson.builder().baseUrl("http://localhost:9090").token("validToken").build()))
                .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withInvalidJenkinsInfoToken() {
        return SaveProductRequestJson.builder()
                .name("test12")
                .userId(123L)
                .jenkinsInfo(Optional.of(JenkinsInfoRequestJson.builder().baseUrl("http://localhost:9090").username("valid_user").build()))
                .build();
    }
}
