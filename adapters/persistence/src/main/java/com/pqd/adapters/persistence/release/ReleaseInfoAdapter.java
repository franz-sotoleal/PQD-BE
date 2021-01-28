package com.pqd.adapters.persistence.release;

import com.pqd.adapters.persistence.release.jira.JiraSprintEntity;
import com.pqd.application.domain.release.ReleaseInfo;
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
        ReleaseInfoEntity releaseInfoEntity = ReleaseInfoEntity.buildReleaseInfoEnity(releaseInfo);
        List<JiraSprintEntity> jiraSprintEntity = releaseInfoEntity.getJiraSprintEntity();
        releaseInfoEntity.setJiraSprintEntity(null);
        ReleaseInfoEntity savedReleaseInfo = repository.save(releaseInfoEntity);
        savedReleaseInfo.setJiraSprintEntity(
                jiraSprintEntity.stream()
                                .peek(entity -> {
                                    entity.setReleaseInfo(savedReleaseInfo);
                                    entity.setIssues(entity.getIssues().stream()
                                                           .peek(issue -> issue.setSprint(entity))
                                                           .collect(Collectors.toList()));
                                })
                                .collect(Collectors.toList()));

        // Set DB generated id to release_info_jira sprint
        ReleaseInfoEntity updatedReleaseInfo = repository.save(savedReleaseInfo);
        return ReleaseInfoEntity.buildReleaseInfo(updatedReleaseInfo);
    }

    @Override
    public List<ReleaseInfo> findAllByProductId(Long productId) {
        List<ReleaseInfoEntity> entities = repository.findAllByProductIdOrderByIdDesc(productId);
        return entities.stream().map(ReleaseInfoEntity::buildReleaseInfo).collect(Collectors.toList());
    }

    public void deleteAllByProductId(Long productId) {
        List<ReleaseInfoEntity> releaseInfoEntities = repository.findAllByProductIdOrderByIdDesc(productId);
        repository.deleteAll(releaseInfoEntities);
    }

}
