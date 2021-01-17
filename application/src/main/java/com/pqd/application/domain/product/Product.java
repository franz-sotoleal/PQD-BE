package com.pqd.application.domain.product;

import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class Product {

    Long id;

    String name;

    String token; // token for authorization on requests to messaging adapter

    SonarqubeInfo sonarqubeInfo;

    public boolean hasValidSonarqubeInfo() {
        return sonarqubeInfo != null
               && sonarqubeInfo.getBaseUrl() != null
               && sonarqubeInfo.getComponentName() != null
               && sonarqubeInfo.getBaseUrl().length() > 0
               && sonarqubeInfo.getComponentName().length() > 0;
    }

}
