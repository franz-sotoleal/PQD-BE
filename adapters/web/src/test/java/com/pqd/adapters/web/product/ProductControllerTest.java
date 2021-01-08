package com.pqd.adapters.web.product;

import com.pqd.adapters.web.product.json.*;
import com.pqd.adapters.web.security.jwt.JwtTokenUtil;
import com.pqd.adapters.web.security.jwt.JwtUserProductClaim;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.sonarqube.SonarqubeConnectionResult;
import com.pqd.application.usecase.claim.SaveClaim;
import com.pqd.application.usecase.product.GetProductList;
import com.pqd.application.usecase.product.SaveProduct;
import com.pqd.application.usecase.product.UpdateProduct;
import com.pqd.application.usecase.release.GetProductReleaseInfo;
import com.pqd.application.usecase.sonarqube.TestSonarqubeConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    private SaveProduct saveProduct;

    private UpdateProduct updateProduct;

    private SaveClaim saveClaim;

    private ProductController controller;

    private GetProductList getProductList;

    private JwtTokenUtil jwtTokenUtil;

    private GetProductReleaseInfo getProductReleaseInfo;

    private TestSonarqubeConnection testSonarqubeConnection;

    @Captor
    private ArgumentCaptor<SaveProduct.Request> saveProductRequestCaptor;

    @Captor
    private ArgumentCaptor<SaveClaim.Request> saveClaimRequestCaptor;

    @BeforeEach
    void setup() {
        saveProduct = mock(SaveProduct.class);
        updateProduct = mock(UpdateProduct.class);
        saveClaim = mock(SaveClaim.class);
        getProductList = mock(GetProductList.class);
        jwtTokenUtil = mock(JwtTokenUtil.class);
        getProductReleaseInfo = mock(GetProductReleaseInfo.class);
        testSonarqubeConnection = mock(TestSonarqubeConnection.class);
        controller = new ProductController(saveProduct, updateProduct, saveClaim, getProductList, jwtTokenUtil, getProductReleaseInfo,
                                           testSonarqubeConnection);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_valid_data_WHEN_updating_product_THEN_product_updated() {
        UpdateProductRequestJson updateProductRequestJson = TestDataGenerator.generateUpdateProductRequestJson();
        Product product = TestDataGenerator.generateProduct();
        List<JwtUserProductClaim> productClaims = TestDataGenerator.generateProductClaimsFromToken();
        when(jwtTokenUtil.getProductClaimsFromToken(any())).thenReturn(productClaims);
        when(updateProduct.execute(any())).thenReturn(UpdateProduct.Response.of(product));

        ResponseEntity<ProductResultJson> actual =
                controller.updateProduct("Bearer token", updateProductRequestJson, 1L);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody().getId()).isEqualTo(product.getId());
        assertThat(actual.getBody().getToken()).isEqualTo(product.getToken());
        assertThat(actual.getBody().getName()).isEqualTo(product.getName());
        assertThat(actual.getBody().getSonarqubeInfo().getComponentName()).isEqualTo(product.getSonarqubeInfo().getComponentName());
        assertThat(actual.getBody().getSonarqubeInfo().getBaseUrl()).isEqualTo(product.getSonarqubeInfo().getBaseUrl());
        assertThat(actual.getBody().getSonarqubeInfo().getToken()).isEqualTo(product.getSonarqubeInfo().getToken());
    }

    @Test
    void GIVEN_product_id_not_inside_jwt_claims_WHEN_updating_product_THEN_exception_thrown() {
        UpdateProductRequestJson updateProductRequestJson = TestDataGenerator.generateUpdateProductRequestJson();
        List<JwtUserProductClaim> productClaims = TestDataGenerator.generateProductClaimsFromToken();
        when(jwtTokenUtil.getProductClaimsFromToken(any())).thenReturn(productClaims);

        Exception exception =
                assertThrows(Exception.class, () -> controller.updateProduct("Bearer token", updateProductRequestJson, 1234L));

        assertThat(exception).hasStackTraceContaining("The product does not exist or you don't have access rights");
    }

    @Test // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_missing_baseurl_WHEN_updating_product_THEN_exception_thrown() {
        UpdateProductRequestJson updateProductRequestJson = TestDataGenerator.generateUpdateProductRequestJson_missingBaseurl();
        List<JwtUserProductClaim> productClaims = TestDataGenerator.generateProductClaimsFromToken();
        when(jwtTokenUtil.getProductClaimsFromToken(any())).thenReturn(productClaims);

        Exception exception =
                assertThrows(Exception.class,
                             () -> controller.updateProduct("Bearer token", updateProductRequestJson, 1L));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_empty_baseurl_WHEN_updating_product_THEN_exception_thrown() {
        UpdateProductRequestJson updateProductRequestJson = TestDataGenerator.generateUpdateProductRequestJson_emptyBaseurl();
        List<JwtUserProductClaim> productClaims = TestDataGenerator.generateProductClaimsFromToken();
        when(jwtTokenUtil.getProductClaimsFromToken(any())).thenReturn(productClaims);

        Exception exception =
                assertThrows(Exception.class,
                             () -> controller.updateProduct("Bearer token", updateProductRequestJson, 1L));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_missing_component_name_WHEN_updating_product_THEN_exception_thrown() {
        UpdateProductRequestJson updateProductRequestJson = TestDataGenerator.generateUpdateProductRequestJson_missingComponentName();
        List<JwtUserProductClaim> productClaims = TestDataGenerator.generateProductClaimsFromToken();
        when(jwtTokenUtil.getProductClaimsFromToken(any())).thenReturn(productClaims);

        Exception exception =
                assertThrows(Exception.class,
                             () -> controller.updateProduct("Bearer token", updateProductRequestJson, 1L));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_empty_component_name_WHEN_updating_product_THEN_exception_thrown() {
        UpdateProductRequestJson updateProductRequestJson = TestDataGenerator.generateUpdateProductRequestJson_emptyComponentName();
        List<JwtUserProductClaim> productClaims = TestDataGenerator.generateProductClaimsFromToken();
        when(jwtTokenUtil.getProductClaimsFromToken(any())).thenReturn(productClaims);

        Exception exception =
                assertThrows(Exception.class,
                             () -> controller.updateProduct("Bearer token", updateProductRequestJson, 1L));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test
    void GIVEN_missing_name_WHEN_updating_product_THEN_exception_thrown() {
        UpdateProductRequestJson updateProductRequestJson = TestDataGenerator.generateUpdateProductRequestJson_missingName();
        List<JwtUserProductClaim> productClaims = TestDataGenerator.generateProductClaimsFromToken();
        when(jwtTokenUtil.getProductClaimsFromToken(any())).thenReturn(productClaims);

        Exception exception =
                assertThrows(Exception.class,
                             () -> controller.updateProduct("Bearer token", updateProductRequestJson, 1L));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test
    void GIVEN_empty_name_WHEN_updating_product_THEN_exception_thrown() {
        UpdateProductRequestJson updateProductRequestJson = TestDataGenerator.generateUpdateProductRequestJson_emptyName();
        List<JwtUserProductClaim> productClaims = TestDataGenerator.generateProductClaimsFromToken();
        when(jwtTokenUtil.getProductClaimsFromToken(any())).thenReturn(productClaims);

        Exception exception =
                assertThrows(Exception.class,
                             () -> controller.updateProduct("Bearer token", updateProductRequestJson, 1L));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test
    void GIVEN_all_correct_WHEN_testing_sonarqube_connection_THEN_connection_result_returned() {
        SonarqubeConnectionResult connectionResult = TestDataGenerator.generateSonarqubeConnectionResult();
        when(testSonarqubeConnection.execute(any())).thenReturn(TestSonarqubeConnection.Response.of(connectionResult));

        var actual = controller.testSonarqubeConnection(SonarqubeInfoRequestJson.builder()
                                                                                .baseUrl("a")
                                                                                .componentName("a")
                                                                                .token("a")
                                                                                .build());
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isInstanceOf(SonarqubeConnectionResultJson.class);
        assertThat(actual.getBody().isConnectionOk()).isEqualTo(true);
        assertThat(actual.getBody().getMessage()).isEqualTo("ok");
    }

    @Test
    void GIVEN_missing_baseurl_WHEN_testing_sonarqube_connection_THEN_exception_thrown() {
        Exception exception =
                assertThrows(Exception.class,
                             () -> controller.testSonarqubeConnection(SonarqubeInfoRequestJson.builder()
                                                                                              .componentName("a")
                                                                                              .token("a")
                                                                                              .build()));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test
    void GIVEN_empty_baseurl_WHEN_testing_sonarqube_connection_THEN_exception_thrown() {
        Exception exception =
                assertThrows(Exception.class,
                             () -> controller.testSonarqubeConnection(SonarqubeInfoRequestJson.builder()
                                                                                              .baseUrl("")
                                                                                              .componentName("a")
                                                                                              .token("a")
                                                                                              .build()));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test
    void GIVEN_missing_component_name_WHEN_testing_sonarqube_connection_THEN_exception_thrown() {
        Exception exception =
                assertThrows(Exception.class,
                             () -> controller.testSonarqubeConnection(SonarqubeInfoRequestJson.builder()
                                                                                              .baseUrl("a")
                                                                                              .token("a")
                                                                                              .build()));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test
    void GIVEN_empty_component_name_WHEN_testing_sonarqube_connection_THEN_exception_thrown() {
        Exception exception =
                assertThrows(Exception.class,
                             () -> controller.testSonarqubeConnection(SonarqubeInfoRequestJson.builder()
                                                                                              .baseUrl("a")
                                                                                              .componentName("")
                                                                                              .token("a")
                                                                                              .build()));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test
    void GIVEN_all_correct_WHEN_getting_product_release_info_THEN_product_release_info_list_returned() {
        List<JwtUserProductClaim> productClaims = TestDataGenerator.generateProductClaimsFromToken();
        ReleaseInfo releaseInfo = TestDataGenerator.generateReleaseInfo();
        ReleaseInfo releaseInfo2 = TestDataGenerator.generateReleaseInfo2();
        when(getProductReleaseInfo.execute(any()))
                .thenReturn(GetProductReleaseInfo.Response.of(List.of(releaseInfo, releaseInfo2)));
        when(jwtTokenUtil.getProductClaimsFromToken(any())).thenReturn(productClaims);

        ResponseEntity<List<ReleaseInfoResultJson>> response =
                controller.getProductReleaseInfo("Bearer token123.bla.bla", 1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(2);
        assertThat(response.getBody().get(0)).isEqualTo(ReleaseInfoResultJson.buildResultJson(releaseInfo));
        assertThat(response.getBody().get(1)).isEqualTo(ReleaseInfoResultJson.buildResultJson(releaseInfo2));
    }

    @Test
    void GIVEN_all_correct_but_no_release_info_WHEN_getting_product_release_info_THEN_empty_list_returned() {
        List<JwtUserProductClaim> productClaims = TestDataGenerator.generateProductClaimsFromToken();
        when(getProductReleaseInfo.execute(any())).thenReturn(GetProductReleaseInfo.Response.of(List.of()));
        when(jwtTokenUtil.getProductClaimsFromToken(any())).thenReturn(productClaims);

        ResponseEntity<List<ReleaseInfoResultJson>> response =
                controller.getProductReleaseInfo("Bearer token123.bla.bla", 2L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(0);
    }

    @Test
    void GIVEN_product_id_not_inside_jwt_claims_WHEN_getting_product_release_info_THEN_exception_thrown() {
        List<JwtUserProductClaim> productClaims = TestDataGenerator.generateProductClaimsFromToken();
        when(jwtTokenUtil.getProductClaimsFromToken(any())).thenReturn(productClaims);

        Exception exception =
                assertThrows(Exception.class, () -> controller.getProductReleaseInfo("Bearer token123.bla.bla", 9876L));

        assertThat(exception).hasStackTraceContaining("The product does not exist or you don't have access rights");
    }

    @Test
    void GIVEN_user_has_claims_WHEN_getting_products_THEN_product_list_returned() {
        List<JwtUserProductClaim> productClaims = TestDataGenerator.generateProductClaimsFromToken();
        List<Product> productList = TestDataGenerator.generateProductList();
        when(jwtTokenUtil.getProductClaimsFromToken(any())).thenReturn(productClaims);
        when(getProductList.execute(any())).thenReturn(GetProductList.Response.of(productList));

        ResponseEntity<List<ProductResultJson>> actual =
                controller.getUserProductList("Bearer token.blabla.bla");

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isInstanceOf(ArrayList.class);
        assertThat(actual.getBody().size()).isEqualTo(2);
        assertThat(actual.getBody().get(0)).isEqualTo(ProductResultJson.buildResultJson(productList.get(0)));
        assertThat(actual.getBody().get(1)).isEqualTo(ProductResultJson.buildResultJson(productList.get(1)));
    }

    @Test
    void GIVEN_user_has_no_claims_WHEN_getting_products_THEN_empty_list_returned() {
        when(jwtTokenUtil.getProductClaimsFromToken(any())).thenReturn(List.of());
        when(getProductList.execute(any())).thenReturn(GetProductList.Response.of(List.of()));

        ResponseEntity<List<ProductResultJson>> actual =
                controller.getUserProductList("Bearer token.blabla.bla");

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isInstanceOf(ArrayList.class);
        assertThat(actual.getBody().size()).isEqualTo(0);
    }

    @Test
    void GIVEN_valid_data_WHEN_saving_product_THEN_product_and_claim_saved() {
        SaveProductRequestJson requestJson = TestDataGenerator.generateSaveProductRequestJson();
        SaveClaim.Response saveClaimResponse = TestDataGenerator.generateSaveClaimResponse();
        SaveProduct.Response saveProductResponse = TestDataGenerator.generateSaveProductResponse();
        when(saveProduct.execute(any())).thenReturn(saveProductResponse);
        when(saveClaim.execute(any())).thenReturn(saveClaimResponse);

        ResponseEntity<ProductResultJson> responseEntity = controller.saveProduct(requestJson);

        verify(saveProduct).execute(saveProductRequestCaptor.capture());
        verify(saveClaim).execute(saveClaimRequestCaptor.capture());
        assertThat(saveProductRequestCaptor.getValue().getName()).isEqualTo(requestJson.getName());
        assertThat(saveProductRequestCaptor.getValue().getSonarqubeInfo())
                .isEqualTo(requestJson.getSonarqubeInfo().toSonarqubeInfo());

        assertThat(saveClaimRequestCaptor.getValue().getProductId())
                .isEqualTo(saveClaimResponse.getUserProductClaim().getProductId());
        assertThat(saveClaimRequestCaptor.getValue().getUserId())
                .isEqualTo(saveClaimResponse.getUserProductClaim().getUserId());
        assertThat(saveClaimRequestCaptor.getValue().getClaimLevel())
                .isEqualTo(saveClaimResponse.getUserProductClaim().getClaimLevel());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getToken()).isEqualTo(saveProductResponse.getProduct().getToken());
        assertThat(responseEntity.getBody().getId()).isEqualTo(saveProductResponse.getProduct().getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(saveProductResponse.getProduct().getName());
        assertThat(responseEntity.getBody().getSonarqubeInfo().getComponentName())
                .isEqualTo(saveProductResponse.getProduct().getSonarqubeInfo().getComponentName());
        assertThat(responseEntity.getBody().getSonarqubeInfo().getBaseUrl())
                .isEqualTo(saveProductResponse.getProduct().getSonarqubeInfo().getBaseUrl());
    }

    @Test
    void GIVEN_no_user_id_WHEN_saving_product_THEN_exception_thrown() {
        SaveProductRequestJson saveProductRequestJson = TestDataGenerator.generateSaveProductRequestJson_withNoUserId();

        Exception exception =
                assertThrows(Exception.class, () -> controller.saveProduct(saveProductRequestJson));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test
        // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_no_sq_info_WHEN_saving_product_THEN_exception_thrown() {
        SaveProductRequestJson saveProductRequestJson = TestDataGenerator.generateSaveProductRequestJson_withNoSqInfo();

        Exception exception =
                assertThrows(Exception.class, () -> controller.saveProduct(saveProductRequestJson));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test
        // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_invalid_sq_info_1_WHEN_saving_product_THEN_exception_thrown() {
        SaveProductRequestJson saveProductRequestJson =
                TestDataGenerator.generateSaveProductRequestJson_withInvalidSqInfo();

        Exception exception =
                assertThrows(Exception.class, () -> controller.saveProduct(saveProductRequestJson));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test
        // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_invalid_sq_info_2_WHEN_saving_product_THEN_exception_thrown() {
        SaveProductRequestJson saveProductRequestJson =
                TestDataGenerator.generateSaveProductRequestJson_withInvalidSqInfo2();

        Exception exception =
                assertThrows(Exception.class, () -> controller.saveProduct(saveProductRequestJson));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test
        // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_invalid_sq_info_3_WHEN_saving_product_THEN_exception_thrown() {
        SaveProductRequestJson saveProductRequestJson =
                TestDataGenerator.generateSaveProductRequestJson_withInvalidSqInfo3();

        Exception exception =
                assertThrows(Exception.class, () -> controller.saveProduct(saveProductRequestJson));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test
        // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_invalid_sq_info_4_WHEN_saving_product_THEN_exception_thrown() {
        SaveProductRequestJson saveProductRequestJson =
                TestDataGenerator.generateSaveProductRequestJson_withInvalidSqInfo4();

        Exception exception =
                assertThrows(Exception.class, () -> controller.saveProduct(saveProductRequestJson));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

}
