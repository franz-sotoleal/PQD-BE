package com.pqd.adapters.persistence.product;

import com.pqd.adapters.persistence.product.sonarqube.SonarqubeInfoEntity;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import com.pqd.application.usecase.product.ProductGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@Transactional
@AllArgsConstructor
public class ProductAdapter implements ProductGateway {

    private final ProductRepository repository;

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(this::buildProduct);
    }

    @Override
    public Product save(Product product) {
        ProductEntity savedProductEntity = repository.save(buildProductEntity(product));
        return buildProduct(savedProductEntity);
    }

    private Product buildProduct(ProductEntity entity) {
        return Product.builder()
                      .sonarqubeInfo(
                              SonarqubeInfo.builder()
                                           .baseUrl(entity.getSonarqubeInfoEntity().getBaseUrl())
                                           .componentName(entity.getSonarqubeInfoEntity().getComponentName())
                                           .token(entity.getSonarqubeInfoEntity().getToken())
                                           .id(entity.getSonarqubeInfoEntity().getId())
                                           .build())
                      .name(entity.getName())
                      .token(entity.getToken())
                      .id(entity.getId())
                      .build();
    }

    private ProductEntity buildProductEntity(Product product) {
        return ProductEntity.builder()
                            .name(product.getName())
                            .token(product.getToken())
                            .sonarqubeInfoEntity(SonarqubeInfoEntity.builder()
                                                                    .token(product.getSonarqubeInfo().getToken())
                                                                    .baseUrl(product.getSonarqubeInfo().getBaseUrl())
                                                                    .componentName(product.getSonarqubeInfo().getComponentName())
                                                                    .build())
                            .build();
    }

}
