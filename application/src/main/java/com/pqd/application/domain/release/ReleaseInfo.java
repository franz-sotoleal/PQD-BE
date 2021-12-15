package com.pqd.application.domain.release;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ReleaseInfo {

    Long id;

    Long productId;

    @EqualsAndHashCode.Exclude //testing purposes
    LocalDateTime created;

    Double qualityLevel;

    Optional<ReleaseInfoSonarqube> releaseInfoSonarqube;

    Optional<ReleaseInfoJira> releaseInfoJira;

    Optional<ReleaseInfoJenkins> releaseInfoJenkins;
}
