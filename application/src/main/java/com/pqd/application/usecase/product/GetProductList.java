package com.pqd.application.usecase.product;

import com.pqd.application.domain.product.Product;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@UseCase
public class GetProductList {

    private final GetProduct getProduct;

    public Response execute(Request request) {
        List<Product> productList = request.getProductIds()
                                       .stream()
                                       .map(id -> getProduct.execute(GetProduct.Request.of(id)).getProduct())
                                       .collect(Collectors.toList());
        return Response.of(productList);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {
        List<Product> products;
    }

    @Value(staticConstructor = "of")
    public static class Request {
        List<Long> productIds;
    }
}
