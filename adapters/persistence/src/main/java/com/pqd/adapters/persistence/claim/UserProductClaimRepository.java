package com.pqd.adapters.persistence.claim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserProductClaimRepository extends JpaRepository<UserProductClaimEntity, Long>,
                                                    JpaSpecificationExecutor<UserProductClaimEntity> {
}
