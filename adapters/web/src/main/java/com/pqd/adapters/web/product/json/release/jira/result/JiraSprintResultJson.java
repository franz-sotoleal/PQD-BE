package com.pqd.adapters.web.product.json.release.jira.result;

import com.pqd.application.domain.jira.JiraSprint;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JiraSprintResultJson {

    Long id;

    Long sprintId;

    String name;

    // Integration test purposes
    String start;

    // Integration test purposes
    String end;

    Long boardId;

    String goal;

    String browserUrl;

    List<JiraIssueResultJson> issues;

    public static JiraSprintResultJson buildJiraSprint(JiraSprint jiraSprint) {
        return JiraSprintResultJson.builder()
                         .boardId(jiraSprint.getBoardId())
                         .browserUrl(jiraSprint.getBrowserUrl())
                         .end(jiraSprint.getEnd().toString())
                         .start(jiraSprint.getStart().toString())
                         .goal(jiraSprint.getGoal())
                         .issues(jiraSprint.getIssues().stream()
                                       .map(JiraIssueResultJson::buildJiraIssue)
                                       .collect(Collectors.toList()))
                         .id(jiraSprint.getId())
                         .sprintId(jiraSprint.getSprintId())
                         .name(jiraSprint.getName())
                         .build();
    }

}
