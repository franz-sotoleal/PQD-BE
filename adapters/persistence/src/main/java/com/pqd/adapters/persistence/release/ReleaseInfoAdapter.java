package com.pqd.adapters.persistence.release;

import com.pqd.adapters.persistence.release.sonarqube.ReleaseInfoSonarqubeEntity;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.usecase.release.ReleaseInfoGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<ReleaseInfo> findAllByProductId(Long productId) {
        List<ReleaseInfoEntity> entities = repository.findAllByProductIdOrderByIdDesc(productId);
        return entities.stream().map(this::buildReleaseInfo).collect(Collectors.toList());
    }

     public void deleteAllByProductId(Long productId) {
        List<ReleaseInfoEntity> releaseInfoEntities = repository.findAllByProductIdOrderByIdDesc(productId);
        repository.deleteAll(releaseInfoEntities);
    }

    private ReleaseInfoEntity buildReleaseInfoEnity(ReleaseInfo releaseInfo) {
        return ReleaseInfoEntity.builder()
                                .created(releaseInfo.getCreated())
                                .productId(releaseInfo.getProductId())
                                .sonarqubeReleaseInfoEntity(buildReleaseInfoSonarqubeEntity(releaseInfo.getReleaseInfoSonarqube()))
                                .qualityLevel(releaseInfo.getQualityLevel())
                                .build();
    }

    private ReleaseInfo buildReleaseInfo(ReleaseInfoEntity entity) {
        return ReleaseInfo.builder()
                          .id(entity.getId())
                          .qualityLevel(entity.getQualityLevel())
                          .productId(entity.getProductId())
                          .releaseInfoSonarqube(buildSonarqubeReleaseInfo(entity.getSonarqubeReleaseInfoEntity()))
                          .created(entity.getCreated())
                          .build();
    }

    private ReleaseInfoSonarqube buildSonarqubeReleaseInfo(ReleaseInfoSonarqubeEntity entity) {
        return ReleaseInfoSonarqube.builder()
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

    private ReleaseInfoSonarqubeEntity buildReleaseInfoSonarqubeEntity(ReleaseInfoSonarqube releaseInfo) {
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
