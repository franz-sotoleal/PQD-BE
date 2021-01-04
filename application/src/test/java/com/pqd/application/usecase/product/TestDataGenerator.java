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
}
