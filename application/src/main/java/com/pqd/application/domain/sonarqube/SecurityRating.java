package com.pqd.application.domain.sonarqube;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SecurityRating {

    private final double value;
}
