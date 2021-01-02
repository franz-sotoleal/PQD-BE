package com.pqd.adapters.persistence.release.sonarqube;

import com.pqd.application.domain.sonarqube.SonarqubeReleaseInfo;
import com.pqd.application.usecase.sonarqube.SonarqubeReleaseInfoGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
@AllArgsConstructor
public class SonarqubeReleaseInfoAdapter implements SonarqubeReleaseInfoGateway {

    private final ReleaseInfoSonarqubeRepository repository;

    @Override
    public SonarqubeReleaseInfo save(SonarqubeReleaseInfo releaseInfo) {
        ReleaseInfoSonarqubeEntity entity = repository.save(buildReleaseInfoSonarqubeEntity(releaseInfo));

        return buildSonarqubeReleaseInfo(entity);
    }

    private ReleaseInfoSonarqubeEntity buildReleaseInfoSonarqubeEntity(SonarqubeReleaseInfo releaseInfo) {
        return ReleaseInfoSonarqubeEntity.builder()
                                         .securityRating(releaseInfo.getSecurityRating())
                                         .maintainabilityRating(releaseInfo.getMaintainabilityRating())
                                         .reliabilityRating(releaseInfo.getReliabilityRating())
                                         .securityVulnerabilities(releaseInfo.getSecurityVulnerabilities())
                                         .maintainabilityDebt(releaseInfo.getMaintainabilityDebt())
                                         .maintainabilitySmells(releaseInfo.getMaintainabilitySmells())
                                         .reliabilityBugs(releaseInfo.getReliabilityBugs())
                                         .build();
    }

    private SonarqubeReleaseInfo buildSonarqubeReleaseInfo(ReleaseInfoSonarqubeEntity entity) {
        return SonarqubeReleaseInfo.builder()
                                   .id(entity.getId())
                                   .securityRating(entity.getSecurityRating())
                                   .reliabilityRating(entity.getReliabilityRating())
                                   .maintainabilityRating(entity.getMaintainabilityRating())
                                   .securityVulnerabilities(entity.getSecurityVulnerabilities())
                                   .reliabilityBugs(entity.getReliabilityBugs())
                                   .maintainabilitySmells(entity.getMaintainabilitySmells())
                                   .maintainabilityDebt(entity.getMaintainabilityDebt())
                                   .build();
    }


}
