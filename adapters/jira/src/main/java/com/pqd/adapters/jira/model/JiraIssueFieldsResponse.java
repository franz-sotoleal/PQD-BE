package com.pqd.adapters.jira.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pqd.application.domain.jira.JiraIssueFields;
import com.pqd.application.domain.jira.JiraIssueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JiraIssueFieldsResponse {

    @JsonProperty(value = "issuetype")
    JiraIssueTypeResponse issueType;

    public static JiraIssueFields buildJiraIssueFields(JiraIssueResponse jiraIssueResponse) {
        return JiraIssueFields.builder()
                              .issueType(JiraIssueType.builder()
                                                      .issueId(jiraIssueResponse.getFields()
                                                                        .getIssueType()
                                                                        .getId())
                                                      .description(jiraIssueResponse.getFields()
                                                                            .getIssueType()
                                                                            .getDescription())
                                                      .iconUrl(jiraIssueResponse.getFields()
                                                                        .getIssueType()
                                                                        .getIconUrl())
                                                      .name(jiraIssueResponse.getFields()
                                                                     .getIssueType()
                                                                     .getName())
                                                      .build())
                              .build();
    }
}
