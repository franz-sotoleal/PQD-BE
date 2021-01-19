package com.pqd.adapters.persistence.release.jira;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "jira_issue", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JiraIssue {

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
}
