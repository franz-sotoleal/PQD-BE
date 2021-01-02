package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.sonarqube.SonarqubeReleaseInfo;

public interface SonarqubeReleaseInfoGateway {

    SonarqubeReleaseInfo save(SonarqubeReleaseInfo releaseInfo);
}
