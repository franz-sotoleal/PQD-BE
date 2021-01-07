package com.pqd.adapters.persistence.product;

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

public class ProductAdapterTest {

    private ProductRepository repository;
    private ProductAdapter adapter;

    @Captor
    private ArgumentCaptor<ProductEntity> captor;


    @BeforeEach
    void setup() {
        repository = mock(ProductRepository.class);
        adapter = new ProductAdapter(repository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_product_entity_exists_WHEN_product_searched_by_id_THEN_product_returned() {
        ProductEntity productEntity = TestDataGenerator.generateProductEntity();
        when(repository.findById(productEntity.getId())).thenReturn(Optional.of(productEntity));

        Optional<Product> result = adapter.findById(productEntity.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(TestDataGenerator.generateProduct());
    }

    @Test
    void GIVEN_product_entity_does_not_exist_WHEN_product_searched_by_id_THEN_nothing_returned() {
        when(repository.findById(123L)).thenReturn(Optional.empty());

        Optional<Product> result = adapter.findById(123L);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void GIVEN_user_product_claim_WHEN_saving_entity_THEN_entity_passed_and_saved() {
        Product product = TestDataGenerator.generateProduct();
        ProductEntity productEntity = TestDataGenerator.generateProductEntity();
        when(repository.save(any())).thenReturn(productEntity);

        Product actual = adapter.save(product);

        verify(repository).save(captor.capture());
        assertThat(captor.getValue()).hasSameClassAs(productEntity);
        assertThat(actual).isEqualTo(product);
    }
}
