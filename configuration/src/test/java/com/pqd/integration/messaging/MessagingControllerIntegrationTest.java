package com.pqd.integration.messaging;

import com.pqd.integration.TestContainerBase;
import com.pqd.integration.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class MessagingControllerIntegrationTest extends TestContainerBase {

    @Autowired
    MockMvc mvc;

    @Test
    @Transactional
    void GIVEN_valid_token_WHEN_release_info_collect_triggered_THEN_ok_returned() throws Exception {
        HttpHeaders httpHeaders = TestDataGenerator.generateValidHttpHeadersForMessagingController();

        mvc.perform(post("/api/messaging/trigger?productId=1")
                            .headers(httpHeaders)
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void GIVEN_invalid_token_WHEN_release_info_collect_triggered_THEN_401_returned() throws Exception {
        HttpHeaders httpHeaders = TestDataGenerator.generateInvalidTokenHttpHeadersForMessagingController();

        mvc.perform(post("/api/messaging/trigger?productId=1")
                            .headers(httpHeaders)
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    void GIVEN_invalid_product_WHEN_release_info_collect_triggered_THEN_401_returned() throws Exception {
        HttpHeaders httpHeaders = TestDataGenerator.generateInvalidTokenHttpHeadersForMessagingController();

        mvc.perform(post("/api/messaging/trigger?productId=123654")
                            .headers(httpHeaders)
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    void GIVEN_invalid_header_name_WHEN_release_info_collect_triggered_THEN_500_returned() throws Exception {
        HttpHeaders httpHeaders = TestDataGenerator.generateInvalidHttpHeadersForMessagingController();

        mvc.perform(post("/api/messaging/trigger?productId=123654")
                            .headers(httpHeaders)
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().is5xxServerError());
    }
}
