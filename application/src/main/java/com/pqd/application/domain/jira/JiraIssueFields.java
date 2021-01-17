package com.pqd.application.domain.jira;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class JiraIssueFields {

    private final JiraIssueType issueType;

    public static JiraIssueFields buildJiraIssueFields(JiraIssue jiraIssue) {
        return JiraIssueFields.builder()
                              .issueType(JiraIssueType.builder()
                                                      .issueId(jiraIssue.getFields()
                                                                        .getIssueType()
                                                                        .getId())
                                                      .description(jiraIssue.getFields()
                                                                            .getIssueType()
                                                                            .getDescription())
                                                      .iconUrl(jiraIssue.getFields()
                                                                        .getIssueType()
                                                                        .getIconUrl())
                                                      .name(jiraIssue.getFields()
                                                                     .getIssueType()
                                                                     .getName())
                                                      .build())
                              .build();
    }
}
