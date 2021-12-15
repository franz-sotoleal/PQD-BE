package com.pqd.application.domain.jenkins;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class JenkinsJob {

    String name;

    String color;
}
