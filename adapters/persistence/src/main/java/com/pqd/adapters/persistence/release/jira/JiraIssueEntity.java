package com.pqd.adapters.persistence.release.jira;

import com.pqd.application.domain.jira.JiraIssue;
import com.pqd.application.domain.jira.JiraIssueFields;
import com.pqd.application.domain.jira.JiraIssueType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "jira_issue", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JiraIssueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jira_issue_seq")
    @SequenceGenerator(name = "jira_issue_generator", sequenceName = "jira_issue_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ToString.Exclude // Lombok toString() causes circular dependency and stackOverflowError
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="jira_sprint_id", nullable=false)
    private JiraSprintEntity sprint;

    @Column(name = "issue_id")
    private Long issueId;

    @Column(name = "key")
    private String key;

    @Column(name = "description")
    private String description;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "name")
    private String name;

    @Column(name = "browser_url")
    private String browserUrl;

    public static JiraIssue buildJiraIssue(JiraIssueEntity entity) {
        return JiraIssue.builder()
                        .id(entity.getId())
                        .issueId(entity.getIssueId())
                        .key(entity.getKey())
                        .fields(JiraIssueFields.builder()
                                               .issueType(JiraIssueType.builder()
                                                                       .name(entity.getName())
                                                                       .iconUrl(entity.getIconUrl())
                                                                       .description(entity.getDescription())
                                                                       .issueId(entity.getIssueId())
                                                                       .build())
                                               .build())
                        .browserUrl(entity.getBrowserUrl())
                        .build();
    }
}
