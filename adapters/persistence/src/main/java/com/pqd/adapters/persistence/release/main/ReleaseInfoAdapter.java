package com.pqd.adapters.persistence.release.main;

import com.pqd.application.domain.release.ReleaseInfo;
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
                                .releaseInfoSonarqubeId(releaseInfo.getReleaseInfoSonarqubeId())
                                .qualityLevel(releaseInfo.getQualityLevel())
                                .build();
    }

    private ReleaseInfo buildReleaseInfo(ReleaseInfoEntity entity) {
        return ReleaseInfo.builder()
                          .id(entity.getId())
                          .qualityLevel(entity.getQualityLevel())
                          .productId(entity.getProductId())
                          .releaseInfoSonarqubeId(entity.getReleaseInfoSonarqubeId())
                          .created(entity.getCreated())
                          .build();
    }
}
