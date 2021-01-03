package com.pqd.adapters.persistence.product;

import com.pqd.application.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductAdapterTest {

    private ProductRepository productRepository;
    private ProductAdapter productAdapter;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        productAdapter = new ProductAdapter(productRepository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_product_entity_exists_WHEN_product_searched_by_id_THEN_product_returned() {
        ProductEntity productEntity = TestDataGenerator.generateProductEntity();
        when(productRepository.findById(productEntity.getId())).thenReturn(Optional.of(productEntity));

        Optional<Product> result = productAdapter.findById(productEntity.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(TestDataGenerator.generateProduct());
    }

    @Test
    void GIVEN_product_entity_does_not_exist_WHEN_product_searched_by_id_THEN_nothing_returned() {
        when(productRepository.findById(123L)).thenReturn(Optional.empty());

        Optional<Product> result = productAdapter.findById(123L);

        assertThat(result.isPresent()).isFalse();
    }
}
