package com.pqd.application.domain.jira;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class JiraIssueFields {

    private final JiraIssueType issueType;

}
