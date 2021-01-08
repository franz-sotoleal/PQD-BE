package com.pqd.application.usecase.product;

import com.pqd.application.domain.product.Product;

import java.util.Optional;

public interface ProductGateway {

    Optional<Product> findById(Long id);

    Product save(Product product);

    Product update(Product product);

    void delete(Long productId);
}
