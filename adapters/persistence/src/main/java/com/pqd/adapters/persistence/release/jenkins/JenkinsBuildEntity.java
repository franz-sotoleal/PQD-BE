package com.pqd.adapters.persistence.release.jenkins;

import com.pqd.adapters.persistence.release.ReleaseInfoEntity;
import com.pqd.application.domain.jenkins.JenkinsBuild;
import com.pqd.application.domain.release.ReleaseInfoJenkins;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "release_info_jenkins", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JenkinsBuildEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "release_info_sq_seq")
    @SequenceGenerator(name = "release_info_sq_generator", sequenceName = "release_info_sq_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ToString.Exclude // Lombok toString() causes circular dependency and stackOverflowError
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "release_info_id", nullable = false)
    private ReleaseInfoEntity releaseInfo;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "build_score")
    @Min(0)
    @Max(100)
    private Integer buildScore;

    @Column(name = "build_report")
    private String buildReport;

    @Column(name = "last_build")
    private Long lastBuild;

    @Column(name = "deployment_frequency")
    private Integer deploymentFrequency;

    @Column(name = "lead_time_for_change")
    private Double leadTimeForChange;

    @Column(name = "time_to_restore_service")
    private Long timeToRestoreService;

    @Column(name = "change_failure_rate")
    @Min(0)
    @Max(100)
    private Double changeFailureRate;

    public static JenkinsBuild buildReleaseInfoJenkins(JenkinsBuildEntity entity) {
        return JenkinsBuild.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .buildScore(entity.getBuildScore())
                .buildReport(entity.getBuildReport())
                .lastBuild(entity.getLastBuild())
                .deploymentFrequency(entity.getDeploymentFrequency())
                .leadTimeForChange(entity.getLeadTimeForChange())
                .timeToRestoreService(entity.getTimeToRestoreService())
                .changeFailureRate(entity.getChangeFailureRate())
                .build();
    }

    public static JenkinsBuildEntity buildJenkinsBuildEntity(JenkinsBuild releaseInfo) {
        return builder()
                .name(releaseInfo.getName())
                .description(releaseInfo.getDescription())
                .status(releaseInfo.getStatus())
                .buildScore(releaseInfo.getBuildScore())
                .buildReport(releaseInfo.getBuildReport())
                .lastBuild(releaseInfo.getLastBuild())
                .deploymentFrequency(releaseInfo.getDeploymentFrequency())
                .leadTimeForChange(releaseInfo.getLeadTimeForChange())
                .timeToRestoreService(releaseInfo.getTimeToRestoreService())
                .changeFailureRate(releaseInfo.getChangeFailureRate())
                .build();
    }
}

