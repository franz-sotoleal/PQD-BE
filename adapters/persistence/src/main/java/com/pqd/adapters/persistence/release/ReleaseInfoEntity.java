package com.pqd.adapters.persistence.release;

import com.pqd.adapters.persistence.release.jira.JiraSprintEntity;
import com.pqd.adapters.persistence.release.sonarqube.ReleaseInfoSonarqubeEntity;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.release.ReleaseInfoJira;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "release_info", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "release_info_seq")
    @SequenceGenerator(name = "release_info_generator", sequenceName = "release_info_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "quality_level")
    private Double qualityLevel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "release_info_sq_id", referencedColumnName = "id")
    private ReleaseInfoSonarqubeEntity sonarqubeReleaseInfoEntity;

    @OneToMany(mappedBy = "releaseInfo",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<JiraSprintEntity> jiraSprintEntity;

    public static ReleaseInfo buildReleaseInfo(ReleaseInfoEntity entity) {
        ReleaseInfo releaseInfo = ReleaseInfo.builder()
                                             .id(entity.getId())
                                             .qualityLevel(entity.getQualityLevel())
                                             .productId(entity.getProductId())
                                             .releaseInfoSonarqube(Optional.empty())
                                             .releaseInfoJira(Optional.empty())
                                             .created(entity.getCreated())
                                             .build();
        if (entity.getSonarqubeReleaseInfoEntity() != null) {
            releaseInfo.setReleaseInfoSonarqube(Optional.of(ReleaseInfoSonarqubeEntity.buildSonarqubeReleaseInfo(
                    entity.getSonarqubeReleaseInfoEntity())));
        }
        if (entity.getJiraSprintEntity() != null) {
            releaseInfo.setReleaseInfoJira(Optional.of(ReleaseInfoJira.builder()
                                                                      .jiraSprints(
                                                                              entity.getJiraSprintEntity()
                                                                                    .stream()
                                                                                    .map(JiraSprintEntity::buildJiraSprint)
                                                                                    .collect(Collectors.toList()))
                                                                      .build()));
        }

        return releaseInfo;
    }

    public static ReleaseInfoEntity buildReleaseInfoEnity(ReleaseInfo releaseInfo) {
        ReleaseInfoEntity entity = builder()
                .created(releaseInfo.getCreated())
                .productId(releaseInfo.getProductId())
                .qualityLevel(releaseInfo.getQualityLevel())
                .build();

        if (releaseInfo.getReleaseInfoSonarqube().isPresent()) {
            entity.setSonarqubeReleaseInfoEntity(
                    ReleaseInfoSonarqubeEntity.buildReleaseInfoSonarqubeEntity(releaseInfo
                                                                                       .getReleaseInfoSonarqube()
                                                                                       .get()));
        }
        if (releaseInfo.getReleaseInfoJira().isPresent()) {
            entity.setJiraSprintEntity(releaseInfo.getReleaseInfoJira().get().getJiraSprints().stream()
                                                  .map(JiraSprintEntity::buildJiraSprintEntity)
                                                  .collect(Collectors.toList()));
        }

        return entity;
    }
}
