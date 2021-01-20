package com.pqd.adapters.persistence.release.jira;


import com.pqd.adapters.persistence.release.ReleaseInfoEntity;
import com.pqd.application.domain.jira.JiraSprint;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "release_info_jira_sprint", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JiraSprintEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "release_info_jira_sprint_seq")
    @SequenceGenerator(name = "release_info_jira_sprint_generator", sequenceName = "release_info_jira_sprint_seq",
                       allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ToString.Exclude // Lombok toString() causes circular dependency and stackOverflowError
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "release_info_id", nullable = false)
    private ReleaseInfoEntity releaseInfo;

    @Column(name = "sprint_id")
    private Long sprintId;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "name")
    private String name;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "goal")
    private String goal;

    @Column(name = "browser_url")
    private String browserUrl;

    @OneToMany(mappedBy = "sprint",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<JiraIssueEntity> issues;

    public static JiraSprint buildJiraSprint(JiraSprintEntity entity) {
        return JiraSprint.builder()
                              .boardId(entity.getBoardId())
                              .browserUrl(entity.getBrowserUrl())
                              .end(entity.getEndTime())
                              .start(entity.getStartTime())
                              .goal(entity.getGoal())
                              .issues(entity.getIssues().stream()
                                            .map(JiraIssueEntity::buildJiraIssue)
                                            .collect(Collectors.toList()))
                              .id(entity.getId())
                              .sprintId(entity.getSprintId())
                              .name(entity.getName())
                              .build();
    }
}
