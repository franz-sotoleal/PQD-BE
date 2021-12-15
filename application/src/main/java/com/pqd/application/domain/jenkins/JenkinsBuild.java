package com.pqd.application.domain.jenkins;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class JenkinsBuild {

    Long id;

    String name;

    String description;

    String status;

    String buildReport;

    Integer buildScore;

    Long lastBuild;

    Integer deploymentFrequency;

    Double leadTimeForChange;

    Long timeToRestoreService;

    Double changeFailureRate;

    Integer lastBuildNumber;
}
