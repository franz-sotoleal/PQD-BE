package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.sonarqube.SonarqubeReleaseInfo;

public interface SonarqubeGateway {

    //Object getQualityCharacteristics(String baseUrl, String componentName, String token);

    SonarqubeReleaseInfo getSonarqubeReleaseInfo(String baseUrl, String componentName, String token);
}
