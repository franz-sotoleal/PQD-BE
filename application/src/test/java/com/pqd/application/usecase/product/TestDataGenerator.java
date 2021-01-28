package com.pqd.application.usecase.product;

import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;

import java.util.Optional;

public class TestDataGenerator {

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

    public static Product generateUpdatableProduct() {
        return Product.builder()
                      .id(123L)
                      .name("test-product")
                      .token("old-token")
                      .sonarqubeInfo(Optional.of(generateSonarqubeInfo()))
                      .jiraInfo(Optional.of(generateJiraInfo()))
                      .build();
    }

    public static String generateToken() {
        return "am6002capucyii2pjg0dp68q8a7d3j3evy1usiad";
    }
}
