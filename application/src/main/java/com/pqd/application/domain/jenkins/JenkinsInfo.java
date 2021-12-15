package com.pqd.application.domain.jenkins;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.List;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class JenkinsInfo {
    Long id;

    String baseUrl;

    String username;

    String token;

    @Setter Integer lastBuildNumber;
}
