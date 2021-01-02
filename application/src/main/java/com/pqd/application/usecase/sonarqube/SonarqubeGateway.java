package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.sonarqube.SonarqubeReleaseInfo;

public interface SonarqubeGateway { //REST Gateway

    SonarqubeReleaseInfo getSonarqubeReleaseInfo(String baseUrl, String componentName, String token);
}
