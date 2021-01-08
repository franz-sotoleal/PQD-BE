package com.pqd.application.usecase.product;

import com.pqd.application.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateProductTest {

    private GenerateToken generateToken;
    private UpdateProduct updateProduct;
    private ProductGateway gateway;

    @Captor
    private ArgumentCaptor<Product> captor;

    @BeforeEach
    void setup() {
        generateToken = mock(GenerateToken.class);
        gateway = mock(ProductGateway.class);
        updateProduct = new UpdateProduct(gateway, generateToken);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_correct_request_and_new_token_request_WHEN_updating_product_THEN_product_updated_with_new_token() {
        String token = TestDataGenerator.generateToken();
        Product product = TestDataGenerator.generateUpdatableProduct();
        when(generateToken.execute()).thenReturn(GenerateToken.Response.of(token));
        when(gateway.update(any())).thenReturn(product);

        updateProduct.execute(UpdateProduct.Request.of(true, 123L, product));

        verify(gateway).update(captor.capture());
        assertThat(token).isEqualTo(captor.getValue().getToken());
        assertThat(product.getName()).isEqualTo(captor.getValue().getName());
        assertThat(product.getSonarqubeInfo()).isEqualTo(captor.getValue().getSonarqubeInfo());
    }

    @Test
    void GIVEN_correct_request_and_no_new_token_request_WHEN_updating_product_THEN_product_updated_with_old_token() {
        String token = TestDataGenerator.generateToken();
        Product product = TestDataGenerator.generateUpdatableProduct();
        when(generateToken.execute()).thenReturn(GenerateToken.Response.of(token));
        when(gateway.update(any())).thenReturn(product);
        when(gateway.findById(any())).thenReturn(Optional.of(product));

        updateProduct.execute(UpdateProduct.Request.of(false, 123L, product));

        verify(gateway).update(captor.capture());
        assertThat(product.getToken()).isEqualTo(captor.getValue().getToken());
        assertThat(product.getName()).isEqualTo(captor.getValue().getName());
        assertThat(product.getSonarqubeInfo()).isEqualTo(captor.getValue().getSonarqubeInfo());
    }
}
