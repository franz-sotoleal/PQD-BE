package com.pqd.adapters.web.product.json.release.jira.result;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReleaseInfoJiraResultJson {

    List<JiraSprintResultJson> jiraSprints;
}
