package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.sonarqube.SecurityRating;

public interface SonarqubeGateway {

    //Object getQualityCharacteristics(String baseUrl, String componentName, String token);

    SecurityRating getSecurityRating(String baseUrl, String componentName, String token);
}
