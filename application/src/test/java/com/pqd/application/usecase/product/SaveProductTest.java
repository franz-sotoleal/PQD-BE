package com.pqd.application.usecase.product;

import com.pqd.application.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SaveProductTest {

    private GenerateToken generateToken;
    private SaveProduct saveProduct;
    private ProductGateway gateway;

    @Captor
    private ArgumentCaptor<Product> captor;

    @BeforeEach
    void setup() {
        generateToken = mock(GenerateToken.class);
        gateway = mock(ProductGateway.class);
        saveProduct = new SaveProduct(gateway, generateToken);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_correct_request_WHEN_saving_product_THEN_product_saved_with_token() {
        String token = TestDataGenerator.generateToken();
        Product product = TestDataGenerator.generateProduct();
        when(generateToken.execute()).thenReturn(GenerateToken.Response.of(token));
        when(gateway.save(any())).thenReturn(product);

        saveProduct.execute(SaveProduct.Request.of(product.getName(), product.getSonarqubeInfo(), product.getJiraInfo()));

        verify(gateway).save(captor.capture());
        assertThat(token).isEqualTo(captor.getValue().getToken());
        assertThat(product.getName()).isEqualTo(captor.getValue().getName());
        assertThat(product.getSonarqubeInfo()).isEqualTo(captor.getValue().getSonarqubeInfo());
    }
}
