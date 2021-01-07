package com.pqd.application.domain.claim;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class UserProductClaim {

    Long userId;

    Long productId;

    ClaimLevel claimLevel;
}
