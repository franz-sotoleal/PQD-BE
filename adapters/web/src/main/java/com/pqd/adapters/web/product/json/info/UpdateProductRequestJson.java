package com.pqd.adapters.web.product.json.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pqd.adapters.web.product.json.info.jira.JiraInfoRequestJson;
import com.pqd.adapters.web.product.json.info.sonarqube.SonarqubeInfoRequestJson;
import com.pqd.application.domain.product.Product;
import com.pqd.application.usecase.product.UpdateProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequestJson {

    @JsonProperty("generateNewToken")
    boolean generateNewToken;

    @JsonProperty("product")
    UpdatableProduct product;

    public UpdateProduct.Request toUpdateProductRequest(Long productId) {
        return UpdateProduct.Request.of(generateNewToken, productId, product.toProduct());
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdatableProduct {

        @JsonProperty("id")
        Long id;

        @JsonProperty("name")
        String name;

        @JsonProperty("token")
        String token; // token for authorization on requests to messaging adapter

        @JsonProperty("sonarqubeInfo")
        SonarqubeInfoRequestJson sonarqubeInfo;

        @JsonProperty("jiraInfo")
        JiraInfoRequestJson jiraInfo;

        public Product toProduct() {
            Product product = Product.builder()
                                   .id(id)
                                   .name(name)
                                   .token(token)
                                   .sonarqubeInfo(Optional.empty())
                                   .jiraInfo(Optional.empty())
                                   .build();
            if (sonarqubeInfo != null) {
                product.setSonarqubeInfo(Optional.of(sonarqubeInfo.toSonarqubeInfo()));
            }
            if (jiraInfo != null) {
                product.setJiraInfo(Optional.of(jiraInfo.toJiraInfo()));
            }
            return product;
        }

    }

}
