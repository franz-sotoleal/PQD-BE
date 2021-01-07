package com.pqd.adapters.web.product;

import com.pqd.adapters.web.product.json.ProductResultJson;
import com.pqd.adapters.web.product.json.SaveProductRequestJson;
import com.pqd.adapters.web.security.jwt.JwtTokenUtil;
import com.pqd.adapters.web.security.jwt.JwtUserProductClaim;
import com.pqd.application.domain.product.Product;
import com.pqd.application.usecase.claim.SaveClaim;
import com.pqd.application.usecase.product.GetProductList;
import com.pqd.application.usecase.product.SaveProduct;
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

    private SaveClaim saveClaim;

    private ProductController controller;

    private GetProductList getProductList;

    private JwtTokenUtil jwtTokenUtil;

    @Captor
    private ArgumentCaptor<SaveProduct.Request> saveProductRequestCaptor;

    @Captor
    private ArgumentCaptor<SaveClaim.Request> saveClaimRequestCaptor;

    @BeforeEach
    void setup() {
        saveProduct = mock(SaveProduct.class);
        saveClaim = mock(SaveClaim.class);
        getProductList = mock(GetProductList.class);
        jwtTokenUtil = mock(JwtTokenUtil.class);
        controller = new ProductController(saveProduct, saveClaim, getProductList, jwtTokenUtil);
        MockitoAnnotations.initMocks(this);
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
        assertThat(saveProductRequestCaptor.getValue().getSonarqubeInfo()).isEqualTo(requestJson.getSonarqubeInfo().toSonarqubeInfo());

        assertThat(saveClaimRequestCaptor.getValue().getProductId()).isEqualTo(saveClaimResponse.getUserProductClaim().getProductId());
        assertThat(saveClaimRequestCaptor.getValue().getUserId()).isEqualTo(saveClaimResponse.getUserProductClaim().getUserId());
        assertThat(saveClaimRequestCaptor.getValue().getClaimLevel()).isEqualTo(saveClaimResponse.getUserProductClaim().getClaimLevel());

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

    @Test // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_no_sq_info_WHEN_saving_product_THEN_exception_thrown() {
        SaveProductRequestJson saveProductRequestJson = TestDataGenerator.generateSaveProductRequestJson_withNoSqInfo();

        Exception exception =
                assertThrows(Exception.class, () -> controller.saveProduct(saveProductRequestJson));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_invalid_sq_info_1_WHEN_saving_product_THEN_exception_thrown() {
        SaveProductRequestJson saveProductRequestJson = TestDataGenerator.generateSaveProductRequestJson_withInvalidSqInfo();

        Exception exception =
                assertThrows(Exception.class, () -> controller.saveProduct(saveProductRequestJson));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_invalid_sq_info_2_WHEN_saving_product_THEN_exception_thrown() {
        SaveProductRequestJson saveProductRequestJson = TestDataGenerator.generateSaveProductRequestJson_withInvalidSqInfo2();

        Exception exception =
                assertThrows(Exception.class, () -> controller.saveProduct(saveProductRequestJson));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_invalid_sq_info_3_WHEN_saving_product_THEN_exception_thrown() {
        SaveProductRequestJson saveProductRequestJson = TestDataGenerator.generateSaveProductRequestJson_withInvalidSqInfo3();

        Exception exception =
                assertThrows(Exception.class, () -> controller.saveProduct(saveProductRequestJson));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

    @Test // While Sonarqube is only supported product then it is required if saving product
    void GIVEN_invalid_sq_info_4_WHEN_saving_product_THEN_exception_thrown() {
        SaveProductRequestJson saveProductRequestJson = TestDataGenerator.generateSaveProductRequestJson_withInvalidSqInfo4();

        Exception exception =
                assertThrows(Exception.class, () -> controller.saveProduct(saveProductRequestJson));
        assertThat(exception).hasStackTraceContaining("Required field missing, empty or wrong format");
    }

}
