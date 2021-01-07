package com.pqd.application.usecase.claim;

import com.pqd.application.domain.claim.UserProductClaim;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@RequiredArgsConstructor
@UseCase
public class GetUserProductClaims {

    private final UserProductClaimGateway gateway;

    public Response execute(Request request) {
        List<UserProductClaim> claims = gateway.getUserProductClaimsByUsername(request.getUsername());

        return Response.of(claims);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {
        List<UserProductClaim> userProductClaims;
    }

    @Value(staticConstructor = "of")
    public static class Request {
        String username;
    }

}
