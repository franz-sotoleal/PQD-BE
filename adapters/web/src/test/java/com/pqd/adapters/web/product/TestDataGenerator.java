package com.pqd.adapters.web.product;

import com.pqd.adapters.web.product.json.info.SaveProductRequestJson;
import com.pqd.adapters.web.product.json.info.UpdateProductRequestJson;
import com.pqd.adapters.web.product.json.info.sonarqube.SonarqubeInfoRequestJson;
import com.pqd.adapters.web.security.jwt.JwtUserProductClaim;
import com.pqd.application.domain.claim.ClaimLevel;
import com.pqd.application.domain.claim.UserProductClaim;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.domain.sonarqube.SonarqubeConnectionResult;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import com.pqd.application.usecase.claim.SaveClaim;
import com.pqd.application.usecase.product.SaveProduct;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class TestDataGenerator {

    public static SonarqubeConnectionResult generateSonarqubeConnectionResult() {
        return SonarqubeConnectionResult.builder()
                                        .message("ok")
                                        .connectionOk(true)
                                        .build();
    }

    public static ReleaseInfo generateReleaseInfo() {
        return ReleaseInfo.builder()
                          .releaseInfoSonarqube(generateReleaseInfoSonarqube())
                          .productId(1L)
                          .qualityLevel(0.83)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 3),
                                                    LocalTime.of(18, 30, 23)))
                          .build();
    }

    private static ReleaseInfoSonarqube generateReleaseInfoSonarqube() {
        return ReleaseInfoSonarqube.builder()
                                   .securityRating(1.0)
                                   .securityVulnerabilities(2.0)
                                   .reliabilityRating(3.0)
                                   .reliabilityBugs(4.0)
                                   .maintainabilityRating(5.0)
                                   .maintainabilityDebt(6.0)
                                   .maintainabilitySmells(7.0)
                                   .build();
    }

    public static ReleaseInfo generateReleaseInfo2() {
        return ReleaseInfo.builder()
                          .releaseInfoSonarqube(generateReleaseInfoSonarqube())
                          .productId(1L)
                          .qualityLevel(0.5)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 7),
                                                    LocalTime.of(16, 30, 23)))
                          .build();
    }

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

    public static UpdateProductRequestJson generateUpdateProductRequestJson() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .id(1L)
                                                        .name("Before update")
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson())
                                                        .build())
                                       .build();
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson_missingBaseurl() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .id(1L)
                                                        .name("Before update")
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson_invalid())
                                                        .build())
                                       .build();
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson_missingName() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .id(1L)
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson_invalid())
                                                        .build())
                                       .build();
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson_emptyName() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .name("")
                                                        .id(1L)
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson_invalid())
                                                        .build())
                                       .build();
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson_emptyBaseurl() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .id(1L)
                                                        .name("Before update")
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson_invalid2())
                                                        .build())
                                       .build();
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson_missingComponentName() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .id(1L)
                                                        .name("Before update")
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson_invalid3())
                                                        .build())
                                       .build();
    }

    public static UpdateProductRequestJson generateUpdateProductRequestJson_emptyComponentName() {
        return UpdateProductRequestJson.builder()
                                       .generateNewToken(true)
                                       .product(UpdateProductRequestJson.UpdatableProduct
                                                        .builder()
                                                        .id(1L)
                                                        .name("Before update")
                                                        .sonarqubeInfo(
                                                                generateSonarqubeInfoRequestJson_invalid4())
                                                        .build())
                                       .build();
    }

    public static SaveProductRequestJson generateSaveProductRequestJson() {
        return SaveProductRequestJson.builder()
                                     .name("test12")
                                     .userId(123L)
                                     .sonarqubeInfo(generateSonarqubeInfoRequestJson())
                                     .build();
    }

    public static SaveProduct.Response generateSaveProductResponse() {
        return SaveProduct.Response.of(generateProduct());
    }

    public static Product generateProduct() {
        return Product.builder()
                      .id(12367L)
                      .token("product-token")
                      .name("product-name")
                      .sonarqubeInfo(generateSonarqubeInfo())
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
