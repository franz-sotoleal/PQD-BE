package com.pqd.application.usecase.claim;

import com.pqd.application.domain.claim.ClaimLevel;
import com.pqd.application.domain.claim.UserProductClaim;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@UseCase
public class SaveClaim {

    private final UserProductClaimGateway gateway;

    public Response execute(Request request) {
        UserProductClaim savedClaim = gateway.save(UserProductClaim.builder()
                                                                   .userId(request.getUserId())
                                                                   .productId(request.getProductId())
                                                                   .claimLevel(request.getClaimLevel())
                                                                   .build());
        return Response.of(savedClaim);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {
        UserProductClaim userProductClaim;
    }

    @Value(staticConstructor = "of")
    public static class Request {
        Long userId;

        Long productId;

        ClaimLevel claimLevel;
    }

}
