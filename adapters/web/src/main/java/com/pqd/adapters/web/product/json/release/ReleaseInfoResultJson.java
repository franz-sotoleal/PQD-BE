package com.pqd.adapters.web.product.json.release;

import com.pqd.adapters.web.product.json.release.jenkins.result.JenkinsJobResultJson;
import com.pqd.adapters.web.product.json.release.jenkins.result.ReleaseInfoJenkinsResultJson;
import com.pqd.adapters.web.product.json.release.jira.result.JiraSprintResultJson;
import com.pqd.adapters.web.product.json.release.jira.result.ReleaseInfoJiraResultJson;
import com.pqd.adapters.web.product.json.release.sonarqube.result.ReleaseInfoSonarqubeResultJson;
import com.pqd.application.domain.release.ReleaseInfo;
import lombok.*;

import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReleaseInfoResultJson {

    Long id;

    Long productId;

    String created;

    Double qualityLevel;

    ReleaseInfoSonarqubeResultJson releaseInfoSonarqube;

    ReleaseInfoJiraResultJson releaseInfoJira;

    ReleaseInfoJenkinsResultJson releaseInfoJenkins;

    public static ReleaseInfoResultJson buildResultJson(ReleaseInfo releaseInfo) {
        ReleaseInfoResultJson resultJson = ReleaseInfoResultJson.builder()
                                                                .id(releaseInfo.getId())
                                                                .productId(releaseInfo.getProductId())
                                                                .created(releaseInfo.getCreated().toString())
                                                                .qualityLevel(releaseInfo.getQualityLevel())
                                                                .build();

        if (releaseInfo.getReleaseInfoSonarqube().isPresent()) {
            resultJson.setReleaseInfoSonarqube(
                    ReleaseInfoSonarqubeResultJson.buildResultJson(releaseInfo.getReleaseInfoSonarqube().get()));
        }
        if (releaseInfo.getReleaseInfoJira().isPresent()) {
            resultJson.setReleaseInfoJira(
                    ReleaseInfoJiraResultJson.builder()
                                             .jiraSprints(releaseInfo
                                                                  .getReleaseInfoJira()
                                                                  .get()
                                                                  .getJiraSprints()
                                                                  .stream()
                                                                  .map(JiraSprintResultJson::buildJiraSprint)
                                                                  .collect(Collectors.toList()))
                                             .build());
        }
        if (releaseInfo.getReleaseInfoJenkins().isPresent()) {
            resultJson.setReleaseInfoJenkins(
                    ReleaseInfoJenkinsResultJson.builder()
                            .jenkinsJobs(releaseInfo
                                    .getReleaseInfoJenkins()
                                    .get()
                                    .getJenkinsBuilds()
                                    .stream()
                                    .map(JenkinsJobResultJson::buildResultJson)
                                    .collect(Collectors.toList()))
                            .build());
        }
        return resultJson;
    }

}
