package com.pqd.adapters.persistence.claim;

import com.pqd.application.domain.claim.ClaimLevel;
import com.pqd.application.domain.claim.UserProductClaim;

public class TestDataGenerator {

    public static UserProductClaim generateUserProductClaim() {
        return UserProductClaim.builder()
                               .userId(123L)
                               .productId(432L)
                               .claimLevel(ClaimLevel.builder().value(ClaimLevel.ADMIN).build())
                               .build();
    }

    public static UserProductClaimEntity generateUserProductClaimEntity() {
        return UserProductClaimEntity.builder()
                                     .productId(432L)
                                     .userId(123L)
                                     .claimLevel(ClaimLevel.ADMIN)
                                     .build();
    }
}
