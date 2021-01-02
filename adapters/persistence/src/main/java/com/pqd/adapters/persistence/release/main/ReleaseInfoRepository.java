package com.pqd.adapters.persistence.release.main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReleaseInfoRepository extends JpaRepository<ReleaseInfoEntity, Long>,
                                           JpaSpecificationExecutor<ReleaseInfoEntity> {
}
