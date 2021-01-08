package com.pqd.adapters.persistence.product;

import com.pqd.adapters.persistence.product.sonarqube.SonarqubeInfoEntity;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;

public class TestDataGenerator {

    public static ProductEntity generateProductEntity() {
        return ProductEntity.builder()
                            .id(123L)
                            .name("test-product")
                            .token("new-token")
                            .sonarqubeInfoEntity(generateSonarqubeInfoEntity())
                            .build();
    }

    public static Product generateProduct() {
        return Product.builder()
                      .id(123L)
                      .name("test-product")
                      .token("new-token")
                      .sonarqubeInfo(generateSonarqubeInfo())
                      .build();

    }

    public static Product generateUpdatableProduct() {
        return Product.builder()
                      .id(123L)
                      .name("test-product-changed")
                      .token("old-token")
                      .sonarqubeInfo(generateUpdatableSonarqubeInfo())
                      .build();

    }

    public static ProductEntity generateUpdatableProductEntity() {
        return ProductEntity.builder()
                             .id(123L)
                             .name("test-product")
                             .token("new-token")
                             .sonarqubeInfoEntity(generateSonarqubeInfoEntity())
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

    private static SonarqubeInfoEntity generateUpdatableSonarqubeInfoEntity() {
        return SonarqubeInfoEntity.builder()
                                  .baseUrl("baseurl")
                                  .componentName("component-name")
                                  .token("token")
                                  .id(1234L)
                                  .build();
    }

}
