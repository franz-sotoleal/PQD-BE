package com.pqd.adapters.web.product.json.release.sonarqube.result;

import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReleaseInfoSonarqubeResultJson {

    Double securityRating;

    Double reliabilityRating;

    Double maintainabilityRating;

    Double securityVulnerabilities;

    Double reliabilityBugs;

    Double maintainabilityDebt;

    Double maintainabilitySmells;

    public static ReleaseInfoSonarqubeResultJson buildResultJson(ReleaseInfoSonarqube releaseInfoSonarqube) {
        return ReleaseInfoSonarqubeResultJson.builder()
                                             .maintainabilityDebt(releaseInfoSonarqube.getMaintainabilityDebt())
                                             .maintainabilityRating(releaseInfoSonarqube.getMaintainabilityRating())
                                             .maintainabilitySmells(releaseInfoSonarqube.getMaintainabilitySmells())
                                             .reliabilityBugs(releaseInfoSonarqube.getReliabilityBugs())
                                             .reliabilityRating(releaseInfoSonarqube.getReliabilityRating())
                                             .securityRating(releaseInfoSonarqube.getSecurityRating())
                                             .securityVulnerabilities(
                                                     releaseInfoSonarqube.getSecurityVulnerabilities())
                                             .build();

    }
}
