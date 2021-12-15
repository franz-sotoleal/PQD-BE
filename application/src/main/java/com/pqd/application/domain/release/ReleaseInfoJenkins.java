package com.pqd.application.domain.release;

import com.pqd.application.domain.jenkins.JenkinsBuild;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class ReleaseInfoJenkins {

    private final List<JenkinsBuild> jenkinsBuilds;
}


