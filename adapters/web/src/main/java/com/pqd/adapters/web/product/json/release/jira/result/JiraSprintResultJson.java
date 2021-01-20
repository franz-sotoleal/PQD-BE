package com.pqd.adapters.web.product.json.release.jira.result;

import com.pqd.application.domain.jira.JiraSprint;
import lombok.*;

import java.time.LocalDateTime;
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

    LocalDateTime start;

    LocalDateTime end;

    Long boardId;

    String goal;

    String browserUrl;

    List<JiraIssueResultJson> issues;

    public static JiraSprintResultJson buildJiraSprint(JiraSprint jiraSprint) {
        return JiraSprintResultJson.builder()
                         .boardId(jiraSprint.getBoardId())
                         .browserUrl(jiraSprint.getBrowserUrl())
                         .end(jiraSprint.getEnd())
                         .start(jiraSprint.getStart())
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
