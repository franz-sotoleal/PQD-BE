package com.pqd.application.domain.jira;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class JiraIssueType {

    private final Long issueId;

    private final String description;

    private final String iconUrl;

    private final String name;
}
