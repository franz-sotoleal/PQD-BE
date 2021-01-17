package com.pqd.application.usecase.jira;

import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.jira.JiraIssue;
import com.pqd.application.domain.jira.JiraSprint;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@UseCase
@Transactional
public class RetrieveReleaseInfoJira {

    private final JiraGateway jiraGateway;

    public Response execute(Request request) {
        List<JiraSprint> activeSprints = jiraGateway.getActiveSprints(request.getJiraInfo());

        // TODO link issues under sprint
        List<List<JiraIssue>> collect = activeSprints.stream().map(sprint -> {
            return jiraGateway.getSprintIssues(request.getJiraInfo(), sprint.getSprintId());
        }).collect(Collectors.toList());

        return Response.of(activeSprints);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {

        List<JiraSprint> activeSprints;
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Request {

        JiraInfo jiraInfo;
    }

}
