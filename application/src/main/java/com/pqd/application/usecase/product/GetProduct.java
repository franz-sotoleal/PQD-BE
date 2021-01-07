package com.pqd.application.usecase.product;

import com.pqd.application.domain.product.Product;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class GetProduct {

    private final ProductGateway productGateway;

    public Response execute(Request request) {
        Product product = productGateway.findById(request.getProductId())
                                        .orElseThrow(() -> new ProductNotFoundException(
                                                String.format("Product with id %s not found", request.getProductId())));

        return Response.of(product);
    }


    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {
        Product product;
    }

    @Value(staticConstructor = "of")
    public static class Request {
        Long productId;
    }

    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }
}
