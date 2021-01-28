package com.pqd.adapters.persistence.product;

import com.pqd.adapters.persistence.product.jira.JiraInfoEntity;
import com.pqd.adapters.persistence.product.sonarqube.SonarqubeInfoEntity;
import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;

import java.util.Optional;

public class TestDataGenerator {

    public static ProductEntity generateProductEntity() {
        return ProductEntity.builder()
                            .id(123L)
                            .name("test-product")
                            .token("new-token")
                            .sonarqubeInfoEntity(generateSonarqubeInfoEntity())
                            .jiraInfoEntity(generateJiraInfoEntity())
                            .build();
    }

    public static ProductEntity generateProductEntity_withoutSonarqube() {
        return ProductEntity.builder()
                            .id(123L)
                            .name("test-product")
                            .token("new-token")
                            .jiraInfoEntity(generateJiraInfoEntity())
                            .build();
    }

    public static ProductEntity generateProductEntity_withoutJira() {
        return ProductEntity.builder()
                            .id(123L)
                            .name("test-product")
                            .token("new-token")
                            .sonarqubeInfoEntity(generateSonarqubeInfoEntity())
                            .build();
    }

    private static JiraInfoEntity generateJiraInfoEntity() {
        return JiraInfoEntity.builder()
                             .userEmail("user@mail.com")
                             .boardId(1L)
                             .id(102L)
                             .token("token123")
                             .baseUrl("https://pqdunittest.atlassian.net")
                             .build();
    }

    public static Product generateProduct() {
        return Product.builder()
                      .id(123L)
                      .name("test-product")
                      .token("new-token")
                      .sonarqubeInfo(Optional.of(generateSonarqubeInfo()))
                      .jiraInfo(Optional.of(generateJiraInfo()))
                      .build();

    }

    public static Product generateProduct_withoutSonarqube() {
        return Product.builder()
                      .id(123L)
                      .name("test-product")
                      .token("new-token")
                      .sonarqubeInfo(Optional.empty())
                      .jiraInfo(Optional.of(generateJiraInfo()))
                      .build();

    }

    public static Product generateProduct_withoutJira() {
        return Product.builder()
                      .id(123L)
                      .name("test-product")
                      .token("new-token")
                      .sonarqubeInfo(Optional.of(generateSonarqubeInfo()))
                      .jiraInfo(Optional.empty())
                      .build();

    }

    public static Product generateUpdatableProduct() {
        return Product.builder()
                      .id(123L)
                      .name("test-product-changed")
                      .token("old-token")
                      .sonarqubeInfo(Optional.of(generateUpdatableSonarqubeInfo()))
                      .build();

    }

    public static ProductEntity generateUpdatableProductEntity() {
        return ProductEntity.builder()
                            .id(123L)
                            .name("test-product")
                            .token("new-token")
                            .sonarqubeInfoEntity(generateSonarqubeInfoEntity())
                            .jiraInfoEntity(generateJiraInfoEntity())
                            .build();

    }

    private static SonarqubeInfoEntity generateSonarqubeInfoEntity() {
        return SonarqubeInfoEntity.builder()
                                  .baseUrl("baseurl")
                                  .componentName("component-name")
                                  .token("token")
                                  .id(1234L)
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

    private static SonarqubeInfo generateUpdatableSonarqubeInfo() {
        return SonarqubeInfo.builder()
                            .baseUrl("baseurl-updated")
                            .componentName("component-name-updated")
                            .token("token-updated")
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

    private static SonarqubeInfoEntity generateUpdatableSonarqubeInfoEntity() {
        return SonarqubeInfoEntity.builder()
                                  .baseUrl("baseurl")
                                  .componentName("component-name")
                                  .token("token")
                                  .id(1234L)
                                  .build();
    }

}
