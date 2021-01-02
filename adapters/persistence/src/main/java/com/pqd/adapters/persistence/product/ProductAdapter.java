package com.pqd.adapters.persistence.product;

import com.pqd.application.domain.product.Product;
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

    private Product buildProduct(ProductEntity entity) {
        return Product.builder()
                      .sonarqubeInfo(
                              Product.SonarqubeInfo.builder()
                                                   .baseUrl(entity.getSonarqubeInfoEntity().getBaseUrl())
                                                   .componentName(entity.getSonarqubeInfoEntity().getComponentName())
                                                   .token(entity.getSonarqubeInfoEntity().getToken())
                                                   .id(entity.getSonarqubeInfoEntity().getId())
                                                   .build())
                      .name(entity.getName())
                      .id(entity.getId())
                      .build();
    }

}
