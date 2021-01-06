package com.pqd.adapters.web.security.jwt;

import com.pqd.application.domain.claim.ClaimLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JwtUserProductClaim {

    Long productId;

    ClaimLevel claimLevel;
}
