package com.pqd.application.usecase.product;

import com.pqd.application.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class DeleteProduct {

    private final ProductGateway gateway;

    public void execute(Request request) {
        gateway.delete(request.getProductId());
    }

    @Value(staticConstructor = "of")
    public static class Request {
        Long productId;
    }
}
