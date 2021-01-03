package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.release.ReleaseInfoSonarqube;

public interface SonarqubeGateway { //REST Gateway

    ReleaseInfoSonarqube getSonarqubeReleaseInfo(String baseUrl, String componentName, String token);
}
