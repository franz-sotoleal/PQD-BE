package com.pqd.application.domain.sonarqube;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class SonarqubeConnectionResult {

    boolean connectionOk;

    String message;
}
