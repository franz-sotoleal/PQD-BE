package com.pqd.adapters.persistence.claim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserProductClaimRepository extends JpaRepository<UserProductClaimEntity, Long>,
                                                    JpaSpecificationExecutor<UserProductClaimEntity> {

    @Query(value = "SELECT * FROM public.user_product_claim WHERE user_id IN (SELECT id FROM public.user WHERE username = ?1) ORDER BY product_id ASC", nativeQuery = true)
    List<UserProductClaimEntity> getUserProductClaimsByUsername(String username);
}
