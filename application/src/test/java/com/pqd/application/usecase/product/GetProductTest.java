package com.pqd.application.usecase.product;

import com.pqd.application.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetProductTest {

    private ProductGateway gateway;
    private GetProduct getProduct;

    @BeforeEach
    void setup() {
        gateway = mock(ProductGateway.class);
        getProduct = new GetProduct(gateway);
    }

    @Test
    void GIVEN_product_exists_WHEN_product_requested_THEN_product_returned() {
        Product existingProduct = TestDataGenerator.generateProduct();

        when(gateway.findById(any())).thenReturn(Optional.of(existingProduct));
        Product actual = getProduct.execute(GetProduct.Request.of(existingProduct.getId())).getProduct();

        verify(gateway).findById(existingProduct.getId());
        assertThat(actual).isEqualTo(existingProduct);
    }

    @Test
    void GIVEN_product_does_not_exist_WHEN_product_requested_THEN_exception_thrown() {
        when(gateway.findById(any())).thenReturn(Optional.empty());


        GetProduct.ProductNotFoundException exception =
                assertThrows(GetProduct.ProductNotFoundException.class, () -> getProduct.execute(GetProduct.Request.of(1L)));

        verify(gateway).findById(1L);
        assertThat(exception).hasStackTraceContaining("ProductNotFound");
    }
}
