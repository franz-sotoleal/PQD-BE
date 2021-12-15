package com.pqd.application.usecase.product;

import com.pqd.application.domain.product.Product;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@UseCase
public class UpdateProduct {

    private final ProductGateway productGateway;

    private final GenerateToken generateToken;

    public Response execute(Request request) {
        String token = productGateway.findById(request.getProductId()).orElse(Product.builder().build()).getToken();
        if (request.isGenerateNewToken()) {
            token = generateToken.execute().getToken();
        }

        Product product = Product.builder()
                                 .id(request.getProductId())
                                 .name(request.getProduct().getName())
                                 .token(token)
                                 .sonarqubeInfo(request.getProduct().getSonarqubeInfo())
                                 .jiraInfo(request.product.getJiraInfo())
                                 .jenkinsInfo(request.product.getJenkinsInfo())
                                 .build();

        return Response.of(productGateway.update(product));
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {
        Product product;
    }

    @Value(staticConstructor = "of")
    public static class Request {
        boolean generateNewToken;

        Long productId;

        Product product;
    }

}
