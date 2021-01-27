package com.pqd.application.domain.sonarqube;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class SonarqubeInfo {

    Long id;

    String baseUrl;

    String componentName;

    String token; // token for authorization on sonarqube api side
}
