package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.domain.sonarqube.SonarqubeConnectionResult;

public interface SonarqubeGateway { //REST Gateway

    ReleaseInfoSonarqube getSonarqubeReleaseInfo(String baseUrl, String componentName, String token);

    SonarqubeConnectionResult testSonarqubeConnection(String baseUrl, String componentName, String token);
}
