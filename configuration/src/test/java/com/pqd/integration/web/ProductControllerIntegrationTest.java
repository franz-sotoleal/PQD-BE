package com.pqd.integration.web;

import com.pqd.adapters.web.product.json.SaveProductRequestJson;
import com.pqd.integration.TestContainerBase;
import com.pqd.integration.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ProductControllerIntegrationTest extends TestContainerBase {

    @Autowired
    MockMvc mvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
    @WithMockUser
    void GIVEN_correct_input_WHEN_saving_product_THEN_status_ok_and_product_returned() throws Exception {
        SaveProductRequestJson requestJson = TestDataGenerator.generateSaveProductRequestJson();
        mvc.perform(post("/api/product/save")
                            .content(objectMapper.writeValueAsString(requestJson))
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
                            .content(objectMapper.writeValueAsString(requestJson))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    @WithMockUser
    void GIVEN_no_user_id_WHEN_saving_product_THEN_status_unauthorized() throws Exception {
        SaveProductRequestJson requestJson = TestDataGenerator.generateSaveProductRequestJson_withNoUserId();
        mvc.perform(post("/api/product/save")
                            .content(objectMapper.writeValueAsString(requestJson))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().is4xxClientError());
    }
}
