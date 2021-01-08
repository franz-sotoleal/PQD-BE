package com.pqd.application.usecase.product;

import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;

public class TestDataGenerator {

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

    public static Product generateUpdatableProduct() {
        return Product.builder()
                      .id(123L)
                      .name("test-product")
                      .token("old-token")
                      .sonarqubeInfo(SonarqubeInfo.builder()
                                                  .baseUrl("base-url")
                                                  .componentName("component-name")
                                                  .token("token")
                                                  .id(1234L)
                                                  .build())
                      .build();
    }

    public static String generateToken() {
        return "am6002capucyii2pjg0dp68q8a7d3j3evy1usiad";
    }
}
