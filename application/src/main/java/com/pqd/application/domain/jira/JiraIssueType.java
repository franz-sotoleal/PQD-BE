package com.pqd.application.domain.jira;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class JiraIssueType {

    private final Long issueId;

    private final String description;

    private final String iconUrl;

    private final String name;
}
