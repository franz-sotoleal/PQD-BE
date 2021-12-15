package com.pqd.adapters.web.product.json.release.jenkins.result;

import com.pqd.adapters.web.product.json.release.jira.result.JiraIssueResultJson;
import com.pqd.adapters.web.product.json.release.sonarqube.result.ReleaseInfoSonarqubeResultJson;
import com.pqd.application.domain.jenkins.JenkinsBuild;
import com.pqd.application.domain.release.ReleaseInfoJenkins;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JenkinsJobResultJson {
    String name;
    String description;
    String status;
    Integer buildScore;
    String buildReport;
    Long lastBuild;
    Integer deploymentFrequency;
    Double leadTimeForChange;
    Long timeToRestoreService;
    Double changeFailureRate;

    public static JenkinsJobResultJson buildResultJson(JenkinsBuild releaseInfoJenkins) {
        return JenkinsJobResultJson.builder()
                .name(releaseInfoJenkins.getName())
                .description(releaseInfoJenkins.getDescription())
                .status(releaseInfoJenkins.getStatus())
                .buildScore(releaseInfoJenkins.getBuildScore())
                .buildReport(releaseInfoJenkins.getBuildReport())
                .lastBuild(releaseInfoJenkins.getLastBuild())
                .deploymentFrequency(releaseInfoJenkins.getDeploymentFrequency())
                .leadTimeForChange(releaseInfoJenkins.getLeadTimeForChange())
                .timeToRestoreService(releaseInfoJenkins.getTimeToRestoreService())
                .changeFailureRate(releaseInfoJenkins.getChangeFailureRate())
                .build();

    }

}
