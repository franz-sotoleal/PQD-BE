package com.pqd.adapters.messaging;

import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TestDataGenerator {
    public static Product generateProduct() {
        return Product.builder()
                      .id(123L)
                      .name("test-product")
                      .token("8257cc3a6b0610da1357f73e03524b090658553a")
                      .sonarqubeInfo(Optional.of(generateSonarqubeInfo()))
                      .build();

    }

    public static Map<String, String> generateHeaders(String tokenBase, String headerKey) {
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));
        Map<String, String> headers = new HashMap<>();
        headers.put(headerKey, basicAuth);
        return headers;
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
