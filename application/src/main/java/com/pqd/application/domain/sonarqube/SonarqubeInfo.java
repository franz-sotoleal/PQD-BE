package com.pqd.application.domain.sonarqube;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class SonarqubeInfo {

    Long id;

    String baseUrl;

    String componentName;

    String token;
}
