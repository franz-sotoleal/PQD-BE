package com.pqd.adapters.persistence.claim;

import com.pqd.application.domain.claim.ClaimLevel;
import com.pqd.application.domain.claim.UserProductClaim;
import com.pqd.application.usecase.claim.UserProductClaimGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
@AllArgsConstructor
public class UserProductClaimAdapter implements UserProductClaimGateway {

    private final UserProductClaimRepository repository;

    @Override
    public UserProductClaim save(UserProductClaim userProductClaim) {
        UserProductClaimEntity savedClaimEntity = repository.save(buildUserProductClaimEntity(userProductClaim));
        return buildUserProductClaim(savedClaimEntity);
    }

    private UserProductClaimEntity buildUserProductClaimEntity(UserProductClaim userProductClaim) {
        return UserProductClaimEntity.builder()
                                     .productId(userProductClaim.getProductId())
                                     .userId(userProductClaim.getUserId())
                                     .claimLevel(userProductClaim.getClaimLevel().getValue())
                                     .build();
    }

    private UserProductClaim buildUserProductClaim(UserProductClaimEntity entity) {
        return UserProductClaim.builder()
                               .userId(entity.getUserId())
                               .productId(entity.getProductId())
                               .claimLevel(ClaimLevel.builder().value(entity.getClaimLevel()).build())
                               .build();
    }
}
