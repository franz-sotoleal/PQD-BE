package com.pqd.adapters.web.product.json.release.jenkins.result;

import com.pqd.adapters.web.product.json.release.jira.result.JiraSprintResultJson;
import com.pqd.application.domain.release.ReleaseInfoJenkins;
import lombok.*;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReleaseInfoJenkinsResultJson {

    List<JenkinsJobResultJson> jenkinsJobs;
}
