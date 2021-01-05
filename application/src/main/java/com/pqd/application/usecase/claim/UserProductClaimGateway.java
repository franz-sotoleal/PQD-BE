package com.pqd.application.usecase.claim;

import com.pqd.application.domain.claim.UserProductClaim;

public interface UserProductClaimGateway {

    UserProductClaim save(UserProductClaim userProductClaim);
}
