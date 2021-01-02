package com.pqd.adapters.persistence.release.sonarqube;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReleaseInfoSonarqubeRepository extends JpaRepository<ReleaseInfoSonarqubeEntity, Long>,
                                                        JpaSpecificationExecutor<ReleaseInfoSonarqubeEntity> {

}
