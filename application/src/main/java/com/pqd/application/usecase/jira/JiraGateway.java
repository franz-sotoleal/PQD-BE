package com.pqd.application.usecase.jira;

import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.jira.JiraSprint;

import java.util.List;

public interface JiraGateway {

    List<JiraSprint> getActiveSprints(JiraInfo jiraInfo);

}
