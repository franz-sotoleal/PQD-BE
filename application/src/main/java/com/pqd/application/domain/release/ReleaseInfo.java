package com.pqd.application.domain.release;

import com.pqd.application.domain.sonarqube.SonarqubeReleaseInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReleaseInfo {

    private final Long id;

    private final Long productId;

    private final LocalDateTime created;

    private final Double qualityLevel;

    private final SonarqubeReleaseInfo sonarqubeReleaseInfo;
}
