package com.pqd.adapters.persistence.product;

import com.pqd.adapters.persistence.product.sonarqube.SonarqubeInfoEntity;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;

public class TestDataGenerator {

    public static ProductEntity generateProductEntity() {
        return ProductEntity.builder()
                            .id(123L)
                            .name("test-product")
                            .sonarqubeInfoEntity(generateSonarqubeInfoEntity())
                            .build();
    }

    public static Product generateProduct() {
        return Product.builder()
                      .id(123L)
                      .name("test-product")
                      .sonarqubeInfo(generateSonarqubeInfo())
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
}
