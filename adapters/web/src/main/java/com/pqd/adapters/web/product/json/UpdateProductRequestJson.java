package com.pqd.adapters.web.product.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pqd.application.domain.product.Product;
import com.pqd.application.usecase.product.UpdateProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

        public Product toProduct() {
            return Product.builder()
                          .id(id)
                          .name(name)
                          .token(token)
                          .sonarqubeInfo(sonarqubeInfo.toSonarqubeInfo())
                          .build();
        }

    }

}
