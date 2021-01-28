package com.pqd.application.domain.release;

import com.pqd.application.domain.jira.JiraSprint;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class ReleaseInfoJira {

    private final List<JiraSprint> jiraSprints;
}
