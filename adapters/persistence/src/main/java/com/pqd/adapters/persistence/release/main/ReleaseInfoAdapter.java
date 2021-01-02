package com.pqd.adapters.persistence.release.main;

import com.pqd.adapters.persistence.release.sonarqube.ReleaseInfoSonarqubeEntity;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.sonarqube.SonarqubeReleaseInfo;
import com.pqd.application.usecase.release.ReleaseInfoGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
@AllArgsConstructor
public class ReleaseInfoAdapter implements ReleaseInfoGateway {

    private final ReleaseInfoRepository repository;

    @Override
    public ReleaseInfo save(ReleaseInfo releaseInfo) {
        ReleaseInfoEntity savedReleaseInfo = repository.save(buildReleaseInfoEnity(releaseInfo));
        return buildReleaseInfo(savedReleaseInfo);
    }

    private ReleaseInfoEntity buildReleaseInfoEnity(ReleaseInfo releaseInfo) {
        return ReleaseInfoEntity.builder()
                                .created(releaseInfo.getCreated())
                                .productId(releaseInfo.getProductId())
                                .sonarqubeReleaseInfoEntity(buildReleaseInfoSonarqubeEntity(releaseInfo.getSonarqubeReleaseInfo()))
                                .qualityLevel(releaseInfo.getQualityLevel())
                                .build();
    }

    private ReleaseInfo buildReleaseInfo(ReleaseInfoEntity entity) {
        return ReleaseInfo.builder()
                          .id(entity.getId())
                          .qualityLevel(entity.getQualityLevel())
                          .productId(entity.getProductId())
                          .sonarqubeReleaseInfo(buildSonarqubeReleaseInfo(entity.getSonarqubeReleaseInfoEntity()))
                          .created(entity.getCreated())
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
}
