package com.pqd.adapters.web.product.json.release.jira.result;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JiraIssueFieldsResultJson {

    JiraIssueTypeResultJson issueType;
}
