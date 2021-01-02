package com.pqd.application.domain.sonarqube;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SonarqubeReleaseInfo {

    private final Long id;

    private final Double securityRating;

    private final Double reliabilityRating;

    private final Double maintainabilityRating;

    private final Double securityVulnerabilities;

    private final Double reliabilityBugs;

    private final Double maintainabilityDebt;

    private final Double maintainabilitySmells;
}
