package com.pqd.adapters.messaging;

import com.pqd.adapters.messaging.async.AsyncService;
import com.pqd.application.domain.product.Product;
import com.pqd.application.usecase.product.GetProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessagingControllerTest {

    private MessagingController controller;
    private GetProduct getProduct;
    private AsyncService asyncService;

    @BeforeEach
    void setup() {
        getProduct = mock(GetProduct.class);
        asyncService = mock(AsyncService.class);
        controller = new MessagingController(asyncService, getProduct);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_valid_token_with_capital_letter_header_WHEN_trigger_request_made_THEN_ok_returned() {
        Product product = TestDataGenerator.generateProduct();
        String tokenBase = product.getToken() + ":";
        Map<String, String> headers = TestDataGenerator.generateHeaders(tokenBase, HttpHeaders.AUTHORIZATION);
        when(getProduct.execute(any())).thenReturn(GetProduct.Response.of(product));

        ResponseEntity<String> response = controller.triggerReleaseInfoCollection(headers, product.getId());

        assertThat(response.getBody())
                .isEqualTo(String.format("Release info collection started for product with id %s", product.getId()));
    }

    @Test
    void GIVEN_valid_token_with_lowercase_header_WHEN_trigger_request_made_THEN_ok_returned() {
        Product product = TestDataGenerator.generateProduct();
        String tokenBase = product.getToken() + ":";
        Map<String, String> headers = TestDataGenerator.generateHeaders(tokenBase, "authorization");
        when(getProduct.execute(any())).thenReturn(GetProduct.Response.of(product));

        ResponseEntity<String> response = controller.triggerReleaseInfoCollection(headers, product.getId());

        assertThat(response.getBody())
                .isEqualTo(String.format("Release info collection started for product with id %s", product.getId()));
    }

    @Test
    void GIVEN_valid_token_with_uppercase_header_WHEN_trigger_request_made_THEN_ok_returned() {
        Product product = TestDataGenerator.generateProduct();
        String tokenBase = product.getToken() + ":";
        Map<String, String> headers = TestDataGenerator.generateHeaders(tokenBase, "AUTHORIZATION");
        when(getProduct.execute(any())).thenReturn(GetProduct.Response.of(product));

        ResponseEntity<String> response = controller.triggerReleaseInfoCollection(headers, product.getId());

        assertThat(response.getBody())
                .isEqualTo(String.format("Release info collection started for product with id %s", product.getId()));
    }

    @Test
    void GIVEN_valid_token_with_invalid_header_name_WHEN_trigger_request_made_THEN_internal_server_error_returned() {
        Product product = TestDataGenerator.generateProduct();
        String tokenBase = product.getToken() + ":";
        Map<String, String> headers = TestDataGenerator.generateHeaders(tokenBase, "AuThOrIzATion");

        Exception exception =
                assertThrows(Exception.class, () -> controller.triggerReleaseInfoCollection(headers, product.getId()));

        assertThat(exception).hasStackTraceContaining("Exception with authorization header");
    }

    @Test
    void GIVEN_no_headers_WHEN_trigger_request_made_THEN_internal_server_error_returned() {
        Product product = TestDataGenerator.generateProduct();

        Exception exception =
                assertThrows(Exception.class, () -> controller.triggerReleaseInfoCollection(new HashMap<>(), product.getId()));

        assertThat(exception).hasStackTraceContaining("Exception with authorization header");
    }

    @Test
    void GIVEN_invalid_token_WHEN_trigger_request_made_THEN_unauthorized_returned() {
        Product product = TestDataGenerator.generateProduct();
        String tokenBase = "invalid_token" + ":";
        Map<String, String> headers = TestDataGenerator.generateHeaders(tokenBase, HttpHeaders.AUTHORIZATION);
        when(getProduct.execute(any())).thenReturn(GetProduct.Response.of(product));

        ResponseEntity<String> response = controller.triggerReleaseInfoCollection(headers, product.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isEqualTo("Invalid token");
    }

}
