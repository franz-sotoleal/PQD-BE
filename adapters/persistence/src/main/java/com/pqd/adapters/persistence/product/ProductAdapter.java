package com.pqd.adapters.persistence.product;

import com.pqd.adapters.persistence.claim.UserProductClaimAdapter;
import com.pqd.adapters.persistence.release.ReleaseInfoAdapter;
import com.pqd.application.domain.product.Product;
import com.pqd.application.usecase.product.ProductGateway;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@Transactional
@AllArgsConstructor
public class ProductAdapter implements ProductGateway {

    private final ProductRepository repository;

    private final ReleaseInfoAdapter releaseInfoAdapter;

    private final UserProductClaimAdapter userProductClaimAdapter;

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(ProductEntity::buildProduct);
    }

    @Override
    public Product save(Product product) {
        ProductEntity savedProductEntity = repository.save(ProductEntity.buildProductEntity(product));
        return ProductEntity.buildProduct(savedProductEntity);
    }

    @Override
    public Product update(Product product) {
        ProductEntity productEntity =
                repository.findById(product.getId())
                          .orElseThrow(() -> new ProductEntityNotFoundException(
                                  String.format("ProductEntity with id %s not found", product.getId())));
        productEntity.setName(product.getName());
        productEntity.setToken(product.getToken());
        productEntity.getSonarqubeInfoEntity().setToken(product.getSonarqubeInfo().getToken());
        productEntity.getSonarqubeInfoEntity().setBaseUrl(product.getSonarqubeInfo().getBaseUrl());
        productEntity.getSonarqubeInfoEntity().setComponentName(product.getSonarqubeInfo().getComponentName());

        ProductEntity savedEntity = repository.save(productEntity);

        return ProductEntity.buildProduct(savedEntity);
    }

    @Override
    public void delete(Long productId) {
        ProductEntity productEntity =
                repository.findById(productId).orElseThrow(() -> new ProductEntityNotFoundException(
                        String.format("ProductEntity with id %s not found", productId)));
        releaseInfoAdapter.deleteAllByProductId(productEntity.getId());
        userProductClaimAdapter.deleteAllByProductId(productEntity.getId());

        repository.delete(productEntity);
    }

    @NoArgsConstructor
    public static class ProductEntityNotFoundException extends NoSuchElementException {
        public ProductEntityNotFoundException(String message) {
            super(message);
        }
    }


}
