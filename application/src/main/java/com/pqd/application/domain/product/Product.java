package com.pqd.application.domain.product;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
public class Product {

    Long id;

    String name;

    SonarqubeInfo sonarqubeInfo;

    @Builder
    @Getter
    @EqualsAndHashCode(callSuper = false)
    public static class SonarqubeInfo {

        Long id;

        String baseUrl;

        String componentName;

        String token;
    }
}
