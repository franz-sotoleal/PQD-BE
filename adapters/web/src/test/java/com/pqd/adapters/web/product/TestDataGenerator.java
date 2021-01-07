package com.pqd.adapters.web.product;

import com.pqd.adapters.web.product.json.SaveProductRequestJson;
import com.pqd.adapters.web.product.json.SonarqubeInfoRequestJson;
import com.pqd.adapters.web.security.jwt.JwtUserProductClaim;
import com.pqd.application.domain.claim.ClaimLevel;
import com.pqd.application.domain.claim.UserProductClaim;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import com.pqd.application.usecase.claim.SaveClaim;
import com.pqd.application.usecase.product.SaveProduct;

import java.util.List;
import java.util.stream.Collectors;

public class TestDataGenerator {

    public static List<JwtUserProductClaim> generateProductClaimsFromToken() {
        return List.of(1L, 2L)
                   .stream()
                   .map(id -> JwtUserProductClaim.builder()
                                                 .productId(id)
                                                 .claimLevel(
                                                         ClaimLevel
                                                                 .builder()
                                                                 .value(ClaimLevel.ADMIN)
                                                                 .build())
                                                 .build()).collect(Collectors.toList());
    }

    public static List<Product> generateProductList() {
        return List.of(1L, 2L)
                   .stream()
                   .map(id -> Product.builder()
                                     .id(id)
                                     .token("product-token" + id)
                                     .name("product-name" + id)
                                     .sonarqubeInfo(generateSonarqubeInfo())
                                     .build()).collect(Collectors.toList());
    }

    public static SaveProductRequestJson generateSaveProductRequestJson() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .userId(123L)
                                     .sonarqubeInfo(generateSonarqubeInfoRequestJson())
                                     .build();
    }

    public static SaveProduct.Response generateSaveProductResponse() {
        return SaveProduct.Response.of(Product.builder()
                                              .id(12367L)
                                              .token("product-token")
                                              .name("product-name")
                                              .sonarqubeInfo(generateSonarqubeInfo())
                                              .build());
    }

    private static SonarqubeInfo generateSonarqubeInfo() {
        return SonarqubeInfo.builder()
                            .baseUrl("baseurl")
                            .componentName("component-name")
                            .token("token")
                            .id(1234L)
                            .build();
    }

    public static SaveClaim.Response generateSaveClaimResponse() {
        return SaveClaim.Response.of(UserProductClaim.builder()
                                                     .claimLevel(ClaimLevel.builder().value(ClaimLevel.ADMIN).build())
                                                     .productId(12367L)
                                                     .userId(123L)
                                                     .build());
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withNoSqInfo() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .userId(123L)
                                     .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withNoUserId() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .sonarqubeInfo(generateSonarqubeInfoRequestJson())
                                     .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withInvalidSqInfo() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .userId(123L)
                                     .sonarqubeInfo(generateSonarqubeInfoRequestJson_invalid())
                                     .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withInvalidSqInfo2() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .userId(123L)
                                     .sonarqubeInfo(generateSonarqubeInfoRequestJson_invalid2())
                                     .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withInvalidSqInfo3() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .userId(123L)
                                     .sonarqubeInfo(generateSonarqubeInfoRequestJson_invalid3())
                                     .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson_withInvalidSqInfo4() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .userId(123L)
                                     .sonarqubeInfo(generateSonarqubeInfoRequestJson_invalid4())
                                     .build();
    }

    private static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson() {
        return SonarqubeInfoRequestJson.builder()
                                       .baseUrl("base-url")
                                       .componentName("comp-name")
                                       .token("tokenabc123")
                                       .build();
    }

    private static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson_invalid() {
        return SonarqubeInfoRequestJson.builder()
                                       .componentName("comp-name")
                                       .token("tokenabc123")
                                       .build();
    }

    private static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson_invalid2() {
        return SonarqubeInfoRequestJson.builder()
                                       .baseUrl("")
                                       .componentName("comp-name")
                                       .token("tokenabc123")
                                       .build();
    }

    private static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson_invalid3() {
        return SonarqubeInfoRequestJson.builder()
                                       .baseUrl("base-url")
                                       .token("tokenabc123")
                                       .build();
    }

    private static SonarqubeInfoRequestJson generateSonarqubeInfoRequestJson_invalid4() {
        return SonarqubeInfoRequestJson.builder()
                                       .baseUrl("base-url")
                                       .componentName("")
                                       .token("tokenabc123")
                                       .build();
    }
}