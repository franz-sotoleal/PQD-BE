package com.pqd.adapters.web.product.json;

import com.pqd.application.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductResultJson {

    Long id;

    String name;

    String token; // token for authorization on requests to messaging adapter

    SonarqubeInfoResultJson sonarqubeInfo;

    public static ProductResultJson buildResultJson(Product product) {
        return ProductResultJson.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .token(product.getToken())
                                .sonarqubeInfo(SonarqubeInfoResultJson
                                                       .builder()
                                                       .baseUrl(product.getSonarqubeInfo().getBaseUrl())
                                                       .componentName(product.getSonarqubeInfo().getComponentName())
                                                       .build())
                                .build();
    }
}
