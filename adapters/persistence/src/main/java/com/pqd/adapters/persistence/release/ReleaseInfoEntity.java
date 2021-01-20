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
        return ReleaseInfo.builder()
                          .id(entity.getId())
                          .qualityLevel(entity.getQualityLevel())
                          .productId(entity.getProductId())
                          .releaseInfoSonarqube(ReleaseInfoSonarqubeEntity.buildSonarqubeReleaseInfo(entity.getSonarqubeReleaseInfoEntity()))
                          .releaseInfoJira(ReleaseInfoJira.builder()
                                                          .jiraSprints(entity.getJiraSprintEntity().stream()
                                                              .map(JiraSprintEntity::buildJiraSprint)
                                                              .collect(Collectors.toList()))
                                                          .build())
                          .created(entity.getCreated())
                          .build();
    }

    public static ReleaseInfoEntity buildReleaseInfoEnity(ReleaseInfo releaseInfo) {
        return builder()
                                .created(releaseInfo.getCreated())
                                .productId(releaseInfo.getProductId())
                                .sonarqubeReleaseInfoEntity(ReleaseInfoSonarqubeEntity
                                                                    .buildReleaseInfoSonarqubeEntity(releaseInfo.getReleaseInfoSonarqube()))
                                .qualityLevel(releaseInfo.getQualityLevel())
                                .build();
    }
}
