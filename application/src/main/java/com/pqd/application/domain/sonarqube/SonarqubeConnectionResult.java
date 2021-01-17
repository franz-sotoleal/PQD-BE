package com.pqd.application.domain.sonarqube;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class SonarqubeConnectionResult { // TODO make common

    boolean connectionOk;

    String message;
}
