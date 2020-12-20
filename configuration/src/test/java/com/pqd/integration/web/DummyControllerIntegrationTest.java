package com.pqd.integration.web;

import com.pqd.integration.TestContainerBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class DummyControllerIntegrationTest extends TestContainerBase {

    @Autowired
    MockMvc mvc;

    @Test
    void GIVEN_invalid_jwt_WHEN_performing_dummy_request_THEN_401_returned() throws Exception {
        mvc.perform(get("/api/dummy/response")
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void GIVEN_jwt_WHEN_performing_dummy_request_THEN_200_returned() throws Exception {
        mvc.perform(get("/api/dummy/response")
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.message", is("Application layer responds for dummy request")));
    }
}
