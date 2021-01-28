package com.pqd.integration;

import com.pqd.adapters.web.authentication.RegisterUserRequestJson;
import com.pqd.adapters.web.product.json.info.SaveProductRequestJson;
import com.pqd.adapters.web.product.json.info.UpdateProductRequestJson;
import com.pqd.adapters.web.product.json.info.jira.JiraInfoRequestJson;
import com.pqd.adapters.web.product.json.info.sonarqube.SonarqubeInfoRequestJson;
import com.pqd.adapters.web.product.json.release.sonarqube.result.ReleaseInfoSonarqubeResultJson;
import com.pqd.adapters.web.security.jwt.JwtRequest;
import com.pqd.integration.special.SaveProductRequestJsonForIntTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Base64;
import java.util.Optional;

public class TestDataGenerator {
    public static ReleaseInfoSonarqubeResultJson generateReleaseInfoSonarqubeResultJson_201() {
        return ReleaseInfoSonarqubeResultJson.builder()
                                             .securityRating(1.0)
                                             .reliabilityRating(2.0)
                                             .maintainabilityRating(1.0)
                                             .securityVulnerabilities(0.0)
                                             .reliabilityBugs(5.0)
                                             .maintainabilityDebt(326.0)
                                             .maintainabilitySmells(65.0)
                                             .build();
    }

    public static ReleaseInfoSonarqubeResultJson generateReleaseInfoSonarqubeResultJson_1() {
        return ReleaseInfoSonarqubeResultJson.builder()
                                             .securityRating(1.0)
                                             .reliabilityRating(3.0)
                                             .maintainabilityRating(2.0)
                                             .securityVulnerabilities(0.0)
                                             .reliabilityBugs(5.0)
                                             .maintainabilityDebt(326.0)
                                             .maintainabilitySmells(65.0)
                                             .build();
    }

    public static JwtRequest generateJwtRequestWithValidCredentials() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword("password");
        jwtRequest.setUsername("user");
        return jwtRequest;
    }

    public static JwtRequest generateJwtRequestWithInvalidCredentials() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword("invalidpass");
        jwtRequest.setUsername("user");
        return jwtRequest;
    }

    public static RegisterUserRequestJson generateRegisterUserRequestJson() {
        return RegisterUserRequestJson.builder()
                                      .username("user1")
                                      .email("john.doe1@mail.com")
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("password5432")
                                      .build();
    }

    public static RegisterUserRequestJson generateRegisterUserRequestJsonWithInvalidUsername() {
        return RegisterUserRequestJson.builder()
                                      .username("use") // too short
                                      .email("john.doe1@mail.com")
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("password5432")
                                      .build();
    }

    public static RegisterUserRequestJson generateRegisterUserRequestJsonWithInvalidPassword() {
        return RegisterUserRequestJson.builder()
                                      .username("user1")
                                      .email("john.doe1@mail.com")
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("pas") // too short
                                      .build();
    }

    public static RegisterUserRequestJson generateRegisterUserRequestJsonWithInvalidEmail() {
        return RegisterUserRequestJson.builder()
                                      .username("user1")
                                      .email("john.doe1.mail") // not email address
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("password5432")
                                      .build();
    }

    public static RegisterUserRequestJson generateRegisterUserRequestJsonWithExistingEmail() {
        return RegisterUserRequestJson.builder()
                                      .username("user1")
                                      .email("john.doe@mail.com")
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("password5432")
                                      .build();
    }

    public static RegisterUserRequestJson generateRegisterUserRequestJsonWithExistingUsername() {
        return RegisterUserRequestJson.builder()
                                      .username("user")
                                      .email("john.doe1@mail.com")
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("password5432")
                                      .build();
    }

    public static HttpHeaders generateValidHttpHeadersForMessagingController() {
        String tokenBase = "8257cc3a6b0610da1357f73e03524b090658553a" + ":";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, basicAuth);
        return headers;
    }

    public static HttpHeaders generateInvalidTokenHttpHeadersForMessagingController() {
        String tokenBase = "invalid" + ":";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, basicAuth);
        return headers;
    }

    public static HttpHeaders generateInvalidHttpHeadersForMessagingController() {
        String tokenBase = "invalid" + ":";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("AuTHORizaTION", basicAuth);
        return headers;
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson_withOldToken() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(false)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .id(1L)
                                                        .name("Demo Product - updated")
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson())
                                                        .jiraInfo(generateJiraInfoRequestJson())
                                                        .build())
                                       .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .userId(123L)
                                     .sonarqubeInfo(Optional.of(generateSonarqubeInfoRequestJson()))
                                     .jiraInfo(Optional.of(generateJiraInfoRequestJson()))
                                     .build();
    }

    public static SaveProductRequestJsonForIntTest generateSaveProductRequestJson_withoutOptionals() {
        return new SaveProductRequestJsonForIntTest(123L,
                                                    "test12",
                                                    generateSonarqubeInfoRequestJson(),
                                                    generateJiraInfoRequestJson());
    }

    public static JiraInfoRequestJson generateJiraInfoRequestJson() {
        return JiraInfoRequestJson.builder()
                                  .userEmail("user@mail.com")
                                  .boardId(1L)
                                  .token("token123")
                                  .baseUrl("https://pqdunittest.atlassian.net")
                                  .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withNoUserId() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .sonarqubeInfo(Optional.of(generateSonarqubeInfoRequestJson()))
                                     .build();
    }

    public static String getExpiredToken() {
        return "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwicHJvZHVjdCI6W3sicHJvZHVj" +
               "dElkIjoxLCJjbGFpbUxldmVsIjp7InZhbHVlIjoiYWRtaW4ifX1dLCJleHAiOjE2MDk5" +
               "NzM2ODEsImlhdCI6MTYwOTk1NTY4MX0.FiMys8RCjnGEPrWrXN1NRR5ia1QMHFq9ceax4" +
               "ckY4KLywzOqOEWX9AJ-0KzFdF3Xyx1p9B-3jNzYlnLRr7wQPA";
    }

    public static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson() {
        return SonarqubeInfoRequestJson.builder()
                                       .baseUrl("base-url")
                                       .componentName("comp-name")
                                       .token("tokenabc123")
                                       .build();
    }

    public static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson_missingBaseUrl() {
        return SonarqubeInfoRequestJson.builder()
                                       .componentName("comp-name")
                                       .token("tokenabc123")
                                       .build();
    }

    public static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson_emptyBaseUrl() {
        return SonarqubeInfoRequestJson.builder()
                                       .baseUrl("")
                                       .componentName("comp-name")
                                       .token("tokenabc123")
                                       .build();
    }

    public static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson_missingComponentName() {
        return SonarqubeInfoRequestJson.builder()
                                       .baseUrl("base-url")
                                       .token("tokenabc123")
                                       .build();
    }

    public static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson_emptyComponentName() {
        return SonarqubeInfoRequestJson.builder()
                                       .componentName("")
                                       .baseUrl("base-url")
                                       .token("tokenabc123")
                                       .build();
    }

    public static JiraInfoRequestJson generateJiraInfoRequestJson_invalidBaseUrl() {
        return JiraInfoRequestJson.builder()
                                  .userEmail("user@mail.com")
                                  .boardId(1L)
                                  .token("token123")
                                  .baseUrl("base-url")
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
}
