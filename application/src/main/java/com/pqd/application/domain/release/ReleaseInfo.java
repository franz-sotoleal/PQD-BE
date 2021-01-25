package com.pqd.application.domain.release;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class ReleaseInfo {

    private final Long id;

    private final Long productId;

    @EqualsAndHashCode.Exclude //testing purposes
    private final LocalDateTime created;

    private final Double qualityLevel;

    private final Optional<ReleaseInfoSonarqube> releaseInfoSonarqube;

    private final Optional<ReleaseInfoJira> releaseInfoJira;
}
