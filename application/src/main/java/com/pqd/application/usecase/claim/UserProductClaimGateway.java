package com.pqd.application.usecase.claim;

import com.pqd.application.domain.claim.UserProductClaim;

import java.util.List;

public interface UserProductClaimGateway {

    UserProductClaim save(UserProductClaim userProductClaim);

    List<UserProductClaim> getUserProductClaimsByUsername(String username);
}
