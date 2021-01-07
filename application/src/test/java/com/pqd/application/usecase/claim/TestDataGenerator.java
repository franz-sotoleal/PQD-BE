package com.pqd.application.usecase.claim;

import com.pqd.application.domain.claim.ClaimLevel;
import com.pqd.application.domain.claim.UserProductClaim;

import java.util.List;

public class TestDataGenerator {

    public static UserProductClaim generateUserProductClaim() {
        return UserProductClaim.builder()
                               .userId(1234L)
                               .productId(56789L)
                               .claimLevel(ClaimLevel.builder().value(ClaimLevel.ADMIN).build())
                               .build();
    }

    public static List<UserProductClaim> generateUserProductClaimList() {
        return List.of(generateUserProductClaim(),
                       UserProductClaim.builder()
                                       .userId(12L)
                                       .productId(56L)
                                       .claimLevel(ClaimLevel.builder().value(ClaimLevel.ADMIN).build())
                                       .build());
    }
}
