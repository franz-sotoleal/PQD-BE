package com.pqd.adapters.web.authentication;

import com.pqd.application.domain.claim.ClaimLevel;
import com.pqd.application.domain.claim.UserProductClaim;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserProductClaimResponseJson {

    Long productId;

    ClaimLevel claimLevel;

    public static UserProductClaimResponseJson buildResponseJson(UserProductClaim userProductClaim) {
        return UserProductClaimResponseJson.builder()
                                           .productId(userProductClaim.getProductId())
                                           .claimLevel(userProductClaim.getClaimLevel())
                                           .build();
    }
}
