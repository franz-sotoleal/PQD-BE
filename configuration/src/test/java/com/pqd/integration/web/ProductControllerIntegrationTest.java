package com.pqd.integration.web;

import com.pqd.adapters.persistence.claim.UserProductClaimRepository;
import com.pqd.adapters.persistence.product.ProductEntity;
import com.pqd.adapters.persistence.product.ProductRepository;
import com.pqd.adapters.web.authentication.LoginResponseJson;
import com.pqd.adapters.web.product.json.*;
import com.pqd.adapters.web.security.jwt.JwtRequest;
import com.pqd.integration.TestContainerBase;
import com.pqd.integration.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ProductControllerIntegrationTest extends TestContainerBase {

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserProductClaimRepository userProductClaimRepository;

    @Test
    @Transactional
    @WithMockUser
    void GIVEN_correct_input_WHEN_saving_product_THEN_status_ok_and_product_returned() throws Exception {
        SaveProductRequestJson requestJson = TestDataGenerator.generateSaveProductRequestJson();
        MvcResult mvcResult = mvc.perform(post("/api/product/save")
                                                  .content(mapper.writeValueAsString(requestJson))
                                                  .contentType(MediaType.APPLICATION_JSON))
                                 .andExpect(status().isOk())
                                 .andExpect(jsonPath("$.token", isA(String.class)))
                                 .andExpect(jsonPath("$.id", isA(Number.class)))
                                 .andExpect(jsonPath("$.name", is(requestJson.getName())))
                                 .andExpect(jsonPath("$.sonarqubeInfo.baseUrl",
                                                     is(requestJson.getSonarqubeInfo().getBaseUrl())))
                                 .andExpect(jsonPath("$.sonarqubeInfo.componentName",
                                                     is(requestJson.getSonarqubeInfo().getComponentName())))
                                 .andReturn();

        ProductResultJson productResultJson =
                mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        ProductEntity entityFromDb = productRepository.findById(productResultJson.getId()).orElse(ProductEntity.builder().build());

        assertThat(entityFromDb.getName()).isEqualTo(requestJson.getName());
        assertThat(entityFromDb.getSonarqubeInfoEntity().getToken())
                .isEqualTo(requestJson.getSonarqubeInfo().getToken());
        assertThat(entityFromDb.getSonarqubeInfoEntity().getComponentName())
                .isEqualTo(requestJson.getSonarqubeInfo().getComponentName());
        assertThat(entityFromDb.getSonarqubeInfoEntity().getBaseUrl())
                .isEqualTo(requestJson.getSonarqubeInfo().getBaseUrl());
    }

    @Test
    @Transactional
    void GIVEN_no_jwt_WHEN_saving_product_THEN_status_unauthorized() throws Exception {
        SaveProductRequestJson requestJson = TestDataGenerator.generateSaveProductRequestJson();
        mvc.perform(post("/api/product/save")
                            .content(mapper.writeValueAsString(requestJson))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    @WithMockUser
    void GIVEN_no_user_id_WHEN_saving_product_THEN_status_unauthorized() throws Exception {
        SaveProductRequestJson requestJson = TestDataGenerator.generateSaveProductRequestJson_withNoUserId();
        mvc.perform(post("/api/product/save")
                            .content(mapper.writeValueAsString(requestJson))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    void GIVEN_user_has_product_claims_WHEN_login_and_updating_product_THEN_status_ok_and_updated_product_returned()
            throws Exception {
        LoginResponseJson loginResponseJson = performLoginRequest();

        UpdateProductRequestJson requestJson = TestDataGenerator.generateUpdateProductRequestJson_withOldToken();
        MvcResult productListMvcResult =
                mvc.perform(put("/api/product/1/update")
                                    .content(mapper.writeValueAsString(requestJson))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header(HttpHeaders.AUTHORIZATION,
                                            "Bearer " + loginResponseJson.getJwt()))
                   .andExpect(status().isOk())
                   .andReturn();
        ProductEntity entityFromDb = productRepository.findById(1L).orElse(ProductEntity.builder().build());
        ProductResultJson productResultJsons =
                mapper.readValue(productListMvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(productResultJsons.getId()).isEqualTo(1L);
        assertThat(productResultJsons.getName()).isEqualTo("Demo Product - updated");
        assertThat(productResultJsons.getToken()).isEqualTo("8257cc3a6b0610da1357f73e03524b090658553a");
        assertThat(productResultJsons.getSonarqubeInfo().getBaseUrl())
                .isEqualTo(requestJson.getProduct().getSonarqubeInfo().getBaseUrl());
        assertThat(productResultJsons.getSonarqubeInfo().getComponentName())
                .isEqualTo(requestJson.getProduct().getSonarqubeInfo().getComponentName());
        assertThat(productResultJsons.getSonarqubeInfo().getToken())
                .isEqualTo(requestJson.getProduct().getSonarqubeInfo().getToken());

        assertThat(entityFromDb.getName()).isEqualTo(requestJson.getProduct().getName());
        assertThat(entityFromDb.getToken()).isEqualTo(productResultJsons.getToken());
        assertThat(entityFromDb.getSonarqubeInfoEntity().getToken())
                .isEqualTo(requestJson.getProduct().getSonarqubeInfo().getToken());
        assertThat(entityFromDb.getSonarqubeInfoEntity().getBaseUrl())
                .isEqualTo(requestJson.getProduct().getSonarqubeInfo().getBaseUrl());
        assertThat(entityFromDb.getSonarqubeInfoEntity().getComponentName())
                .isEqualTo(requestJson.getProduct().getSonarqubeInfo().getComponentName());
    }

    @Test
    @Transactional
    void GIVEN_user_has_no_product_claims_WHEN_login_and_updating_product_THEN_400_returned()
            throws Exception {
        LoginResponseJson loginResponseJson = performLoginRequest();
        UpdateProductRequestJson requestJson = TestDataGenerator.generateUpdateProductRequestJson_withOldToken();

        MvcResult mvcResult =
                mvc.perform(put("/api/product/101/update")
                                    .content(mapper.writeValueAsString(requestJson))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header(HttpHeaders.AUTHORIZATION,
                                            "Bearer " + loginResponseJson.getJwt()))
                   .andExpect(status().is4xxClientError())
                   .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("The product does not exist or you don't have access rights");
    }


    @Test
    @Transactional
    void GIVEN_user_has_product_claims_WHEN_login_and_requesting_all_products_THEN_status_ok_and_user_products_returned()
            throws Exception {
        LoginResponseJson loginResponseJson = performLoginRequest();

        MvcResult productListMvcResult =
                mvc.perform(get("/api/product/get/all")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header(HttpHeaders.AUTHORIZATION,
                                            "Bearer " + loginResponseJson.getJwt()))
                   .andExpect(status().isOk())
                   .andReturn();


        List<ProductResultJson> productResultJsons =
                mapper.readValue(productListMvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(productResultJsons.size()).isEqualTo(2);
        assertThat(productResultJsons.get(0).getId()).isEqualTo(1L);
        assertThat(productResultJsons.get(0).getName()).isEqualTo("Demo Product");
        assertThat(productResultJsons.get(0).getToken()).isEqualTo("8257cc3a6b0610da1357f73e03524b090658553a");
        assertThat(productResultJsons.get(0).getSonarqubeInfo().getBaseUrl()).isEqualTo("http://localhost:9000");
        assertThat(productResultJsons.get(0).getSonarqubeInfo().getComponentName()).isEqualTo("ESI-builtit");
        assertThat(productResultJsons.get(1).getId()).isEqualTo(51L);
        assertThat(productResultJsons.get(1).getName()).isEqualTo("Demo Product 2");
        assertThat(productResultJsons.get(1).getToken()).isEqualTo("7257cc3a6b0610da1357f73e03524b090658553b");
        assertThat(productResultJsons.get(1).getSonarqubeInfo().getBaseUrl()).isEqualTo("http://localhost:9000");
        assertThat(productResultJsons.get(1).getSonarqubeInfo().getComponentName()).isEqualTo("ESI-builtit");
    }

    @Test
    @Transactional
    void GIVEN_invalid_jwt_WHEN_after_login_requesting_all_products_THEN_exception_thrown() // Unauthorized is returned, because the jwt filter intervenes, while actually running the API
            throws Exception {
        JwtRequest jwtRequest = TestDataGenerator.generateJwtRequestWithValidCredentials();
        String expiredToken = TestDataGenerator.getExpiredToken();

        mvc.perform(post("/api/authentication/login")
                            .content(mapper.writeValueAsString(jwtRequest))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andReturn();

        Exception exception =
                assertThrows(Exception.class, () -> mvc.perform(get("/api/product/get/all")
                                                                        .contentType(MediaType.APPLICATION_JSON)
                                                                        .header(HttpHeaders.AUTHORIZATION,
                                                                                "Bearer " +
                                                                                expiredToken.replaceFirst("W", "b"))));
        assertThat(exception).hasStackTraceContaining("SignatureException: JWT signature does not match locally " +
                                                      "computed signature. JWT validity cannot be asserted and" +
                                                      " should not be trusted");
    }

    @Test
    @Transactional
    void GIVEN_user_has_product_claims_WHEN_login_and_requesting_release_info_THEN_status_ok_and_release_info_list_returned()
            throws Exception {
        LoginResponseJson loginResponseJson = performLoginRequest();

        MvcResult releaseInfoListMvcResult =
                mvc.perform(get("/api/product/1/releaseInfo")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header(HttpHeaders.AUTHORIZATION,
                                            "Bearer " + loginResponseJson.getJwt()))
                   .andExpect(status().isOk())
                   .andReturn();

        List<ReleaseInfoResultJson> releaseInfoList =
                mapper.readValue(releaseInfoListMvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(releaseInfoList.stream()
                                  .map(ReleaseInfoResultJson::getProductId).collect(Collectors.toSet())
                                  .size()).isEqualTo(1);
        assertThat(releaseInfoList.get(0).getId()).isEqualTo(201L);
        assertThat(releaseInfoList.get(0).getProductId()).isEqualTo(1L);
        assertThat(releaseInfoList.get(0).getQualityLevel()).isEqualTo(0.8);
        assertThat(releaseInfoList.get(0).getReleaseInfoSonarqube())
                .isEqualTo(TestDataGenerator.generateReleaseInfoSonarqubeResultJson_201());
        assertThat(releaseInfoList.get(1).getId()).isEqualTo(151L);
        assertThat(releaseInfoList.get(1).getProductId()).isEqualTo(1L);
        assertThat(releaseInfoList.get(1).getQualityLevel()).isEqualTo(0.4);
        assertThat(releaseInfoList.get(4).getId()).isEqualTo(1L);
        assertThat(releaseInfoList.get(4).getProductId()).isEqualTo(1L);
        assertThat(releaseInfoList.get(4).getQualityLevel()).isEqualTo(0.75);
        assertThat(releaseInfoList.get(4).getReleaseInfoSonarqube())
                .isEqualTo(TestDataGenerator.generateReleaseInfoSonarqubeResultJson_1());
    }

    @Test
    @Transactional
    void GIVEN_user_has_no_product_claims_WHEN_login_and_requesting_release_info_THEN_400_returned()
            throws Exception {
        LoginResponseJson loginResponseJson = performLoginRequest();

        MvcResult mvcResult =
                mvc.perform(get("/api/product/101/releaseInfo")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header(HttpHeaders.AUTHORIZATION,
                                            "Bearer " + loginResponseJson.getJwt()))
                   .andExpect(status().is4xxClientError())
                   .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("The product does not exist or you don't have access rights");
    }

    @Test
    @Transactional
    @WithMockUser
    void GIVEN_syntactically_correct_request_WHEN_testing_sonarqube_connection_THEN_sonarqube_connection_result_returned()
            throws Exception {
        SonarqubeInfoRequestJson requestJson = TestDataGenerator.generateSonarqubeInfoRequestJson();

        mvc.perform(post("/api/product/test/sonarqube/connection")
                            .content(mapper.writeValueAsString(requestJson))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.connectionOk", is(false)))
           .andExpect(jsonPath("$.message", is("URI is not absolute")));
    }

    @Test
    @Transactional
    @WithMockUser
    void GIVEN_missing_baseurl_WHEN_testing_sonarqube_connection_THEN_400_returned()
            throws Exception {
        SonarqubeInfoRequestJson requestJson = TestDataGenerator.generateSonarqubeInfoRequestJson_missingBaseUrl();

        MvcResult mvcResult = mvc.perform(post("/api/product/test/sonarqube/connection")
                                                  .content(mapper.writeValueAsString(requestJson))
                                                  .contentType(MediaType.APPLICATION_JSON))
                                 .andExpect(status().is4xxClientError())
                                 .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("Required field missing, empty or wrong format");
    }

    @Test
    @Transactional
    @WithMockUser
    void GIVEN_empty_baseurl_WHEN_testing_sonarqube_connection_THEN_400_returned()
            throws Exception {
        SonarqubeInfoRequestJson requestJson = TestDataGenerator.generateSonarqubeInfoRequestJson_emptyBaseUrl();

        MvcResult mvcResult = mvc.perform(post("/api/product/test/sonarqube/connection")
                                                  .content(mapper.writeValueAsString(requestJson))
                                                  .contentType(MediaType.APPLICATION_JSON))
                                 .andExpect(status().is4xxClientError())
                                 .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("Required field missing, empty or wrong format");
    }

    @Test
    @Transactional
    @WithMockUser
    void GIVEN_missing_component_name_WHEN_testing_sonarqube_connection_THEN_400_returned()
            throws Exception {
        SonarqubeInfoRequestJson requestJson =
                TestDataGenerator.generateSonarqubeInfoRequestJson_missingComponentName();

        MvcResult mvcResult = mvc.perform(post("/api/product/test/sonarqube/connection")
                                                  .content(mapper.writeValueAsString(requestJson))
                                                  .contentType(MediaType.APPLICATION_JSON))
                                 .andExpect(status().is4xxClientError())
                                 .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("Required field missing, empty or wrong format");
    }

    @Test
    @Transactional
    @WithMockUser
    void GIVEN_empty_component_name_WHEN_testing_sonarqube_connection_THEN_400_returned()
            throws Exception {
        SonarqubeInfoRequestJson requestJson =
                TestDataGenerator.generateSonarqubeInfoRequestJson_emptyComponentName();

        MvcResult mvcResult = mvc.perform(post("/api/product/test/sonarqube/connection")
                                                  .content(mapper.writeValueAsString(requestJson))
                                                  .contentType(MediaType.APPLICATION_JSON))
                                 .andExpect(status().is4xxClientError())
                                 .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("Required field missing, empty or wrong format");
    }

    private LoginResponseJson performLoginRequest() throws Exception {
        JwtRequest jwtRequest = TestDataGenerator.generateJwtRequestWithValidCredentials();

        MvcResult loginMvcResult = mvc.perform(post("/api/authentication/login")
                                                       .content(mapper.writeValueAsString(jwtRequest))
                                                       .contentType(MediaType.APPLICATION_JSON))
                                      .andExpect(status().isOk())
                                      .andReturn();

        return mapper.readValue(loginMvcResult.getResponse().getContentAsString(), LoginResponseJson.class);
    }

}
