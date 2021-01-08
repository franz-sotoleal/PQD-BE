package com.pqd.adapters.persistence.product;

import com.pqd.application.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void GIVEN_product_WHEN_saving_entity_THEN_entity_passed_and_saved() {
        Product product = TestDataGenerator.generateProduct();
        ProductEntity productEntity = TestDataGenerator.generateProductEntity();
        when(repository.save(any())).thenReturn(productEntity);

        Product actual = adapter.save(product);

        verify(repository).save(captor.capture());
        assertThat(captor.getValue()).hasSameClassAs(productEntity);
        assertThat(actual).isEqualTo(product);
    }

    @Test
    void GIVEN_product_WHEN_updating_entity_THEN_entity_passed_and_updated() {
        Product product = TestDataGenerator.generateProduct();
        ProductEntity updatableProductEntity = TestDataGenerator.generateUpdatableProductEntity();
        ProductEntity productEntity = TestDataGenerator.generateProductEntity();
        when(repository.findById(any())).thenReturn(Optional.of(productEntity));
        when(repository.save(any())).thenReturn(updatableProductEntity);

        Product actual = adapter.update(product);

        verify(repository).save(captor.capture());
        assertThat(captor.getValue()).hasSameClassAs(productEntity);
        assertThat(actual).isEqualTo(product);
    }

    @Test
    void GIVEN_updatable_product_not_found_WHEN_updating_entity_THEN_exception_thrown() {
        Product product = TestDataGenerator.generateProduct();
        ProductEntity updatableProductEntity = TestDataGenerator.generateUpdatableProductEntity();
        when(repository.findById(any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(updatableProductEntity);

        Exception exception =
                assertThrows(Exception.class, () -> adapter.update(product));
        assertThat(exception).hasStackTraceContaining("ProductEntityNotFoundException");
    }
}
