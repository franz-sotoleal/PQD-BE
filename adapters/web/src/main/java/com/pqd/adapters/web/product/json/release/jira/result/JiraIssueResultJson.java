package com.pqd.adapters.web.product.json.release.jira.result;

import com.pqd.application.domain.jira.JiraIssue;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JiraIssueResultJson {

    Long id;

    Long issueId;

    String key;

    JiraIssueFieldsResultJson fields;

    String browserUrl;

    public static JiraIssueResultJson buildJiraIssue(JiraIssue jiraIssue) {
        return JiraIssueResultJson.builder()
                        .id(jiraIssue.getId())
                        .issueId(jiraIssue.getIssueId())
                        .key(jiraIssue.getKey())
                        .fields(JiraIssueFieldsResultJson.builder()
                                               .issueType(JiraIssueTypeResultJson.builder()
                                                                       .name(jiraIssue.getFields().getIssueType().getName())
                                                                       .iconUrl(jiraIssue.getFields().getIssueType().getIconUrl())
                                                                       .description(jiraIssue.getFields().getIssueType().getDescription())
                                                                       .issueId(jiraIssue.getFields().getIssueType().getIssueId())
                                                                       .build())
                                               .build())
                        .browserUrl(jiraIssue.getBrowserUrl())
                        .build();
    }
}
