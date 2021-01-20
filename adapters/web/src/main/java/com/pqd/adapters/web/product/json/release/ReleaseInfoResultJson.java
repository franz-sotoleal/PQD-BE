package com.pqd.adapters.web.product.json.release;

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

    public static ReleaseInfoResultJson buildResultJson(ReleaseInfo releaseInfo) {
        return ReleaseInfoResultJson.builder()
                                    .id(releaseInfo.getId())
                                    .productId(releaseInfo.getProductId())
                                    .created(releaseInfo.getCreated().toString())
                                    .qualityLevel(releaseInfo.getQualityLevel())
                                    .releaseInfoSonarqube(
                                            ReleaseInfoSonarqubeResultJson
                                                    .buildResultJson(releaseInfo.getReleaseInfoSonarqube()))
                                    .releaseInfoJira(ReleaseInfoJiraResultJson.builder()
                                                                              .jiraSprints(releaseInfo
                                                                                                   .getReleaseInfoJira()
                                                                                                   .getJiraSprints()
                                                                                                   .stream()
                                                                                                   .map(JiraSprintResultJson::buildJiraSprint)
                                                                                                   .collect(Collectors.toList()))
                                                                              .build())
                                    .build();
    }

}
