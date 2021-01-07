package com.pqd.application.domain.claim;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public final class ClaimLevel {

    String value;

    public static final String ADMIN = "admin";

    public static final String READ = "read";

}
