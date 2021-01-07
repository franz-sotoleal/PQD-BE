package com.pqd.adapters.persistence.release;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReleaseInfoRepository extends JpaRepository<ReleaseInfoEntity, Long>,
                                           JpaSpecificationExecutor<ReleaseInfoEntity> {

    List<ReleaseInfoEntity> findAllByProductIdOrderByIdDesc(@Param("product_id") Long productId);
}
