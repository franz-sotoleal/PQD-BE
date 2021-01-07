package com.pqd.integration.web;

import com.pqd.adapters.web.authentication.LoginResponseJson;
import com.pqd.adapters.web.product.json.ProductResultJson;
import com.pqd.adapters.web.product.json.ReleaseInfoResultJson;
import com.pqd.adapters.web.product.json.SaveProductRequestJson;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ProductControllerIntegrationTest extends TestContainerBase {

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @Transactional
    @WithMockUser
    void GIVEN_correct_input_WHEN_saving_product_THEN_status_ok_and_product_returned() throws Exception {
        SaveProductRequestJson requestJson = TestDataGenerator.generateSaveProductRequestJson();
        mvc.perform(post("/api/product/save")
                            .content(mapper.writeValueAsString(requestJson))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.token", isA(String.class)))
           .andExpect(jsonPath("$.id", isA(Number.class)))
           .andExpect(jsonPath("$.name", is(requestJson.getName())))
           .andExpect(jsonPath("$.sonarqubeInfo.baseUrl", is(requestJson.getSonarqubeInfo().getBaseUrl())))
           .andExpect(jsonPath("$.sonarqubeInfo.componentName", is(requestJson.getSonarqubeInfo().getComponentName())));
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

        assertThat(releaseInfoList.size()).isEqualTo(5);
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
