package com.pqd.adapters.persistence.release;

import com.pqd.adapters.persistence.release.jira.JiraSprintEntity;
import com.pqd.adapters.persistence.release.sonarqube.ReleaseInfoSonarqubeEntity;
import com.pqd.application.domain.jira.JiraIssue;
import com.pqd.application.domain.jira.JiraIssueFields;
import com.pqd.application.domain.jira.JiraIssueType;
import com.pqd.application.domain.jira.JiraSprint;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.release.ReleaseInfoJira;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class TestDataGenerator {

    public static ReleaseInfoEntity generateReleaseInfoEntity() {
        return ReleaseInfoEntity.builder()
                                .sonarqubeReleaseInfoEntity(generateReleaseInfoSonarqubeEntity())
                                .productId(1L)
                                .qualityLevel(0.83)
                                .created(LocalDateTime.of(LocalDate.of(2021, 1, 3),
                                                          LocalTime.of(18, 30, 23)))
                                .build();
    }

    public static ReleaseInfoEntity generateReleaseInfoEntity_withJira() {
        return ReleaseInfoEntity.builder()
                                .sonarqubeReleaseInfoEntity(generateReleaseInfoSonarqubeEntity())
                                .jiraSprintEntity(List.of(generateJiraSprintEntity()))
                                .productId(1L)
                                .qualityLevel(0.83)
                                .created(LocalDateTime.of(LocalDate.of(2021, 1, 3),
                                                          LocalTime.of(18, 30, 23)))
                                .build();
    }

    private static JiraSprintEntity generateJiraSprintEntity() {
        return JiraSprintEntity.buildJiraSprintEntity(generateJiraSprint());
    }

    public static ReleaseInfo generateReleaseInfo() {
        return ReleaseInfo.builder()
                          .releaseInfoSonarqube(Optional.of(generateReleaseInfoSonarqube()))
                          .releaseInfoJira(Optional.empty())
                          .productId(1L)
                          .qualityLevel(0.83)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 3),
                                                    LocalTime.of(18, 30, 23)))
                          .build();
    }

    public static ReleaseInfo generateReleaseInfo_withoutJira() {
        return ReleaseInfo.builder()
                          .releaseInfoSonarqube(Optional.of(generateReleaseInfoSonarqube()))
                          .releaseInfoJira(Optional.of(ReleaseInfoJira.builder().jiraSprints(List.of()).build()))
                          .productId(1L)
                          .qualityLevel(0.83)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 3),
                                                    LocalTime.of(18, 30, 23)))
                          .build();
    }

    public static ReleaseInfo generateReleaseInfo_withJira() {
        return ReleaseInfo.builder()
                          .releaseInfoSonarqube(Optional.of(generateReleaseInfoSonarqube()))
                          .releaseInfoJira(Optional.of(generateReleaseInfoJira()))
                          .productId(1L)
                          .qualityLevel(0.83)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 3),
                                                    LocalTime.of(18, 30, 23)))
                          .build();
    }

    private static ReleaseInfoJira generateReleaseInfoJira() {
        return ReleaseInfoJira.builder()
                              .jiraSprints(List.of(generateJiraSprint()))
                              .build();
    }

    private static JiraSprint generateJiraSprint() {
        return JiraSprint.builder()
                         .id(null)
                         .sprintId(4L)
                         .name("sprindi nimi 2")
                         .goal("goal 2")
                         .boardId(1L)
                         .start(LocalDateTime.of(LocalDate.of(2021, 1, 16), LocalTime.of(17, 31, 9, 879)))
                         .end(LocalDateTime.of(LocalDate.of(2021, 1, 27), LocalTime.of(14, 30, 9, 879)))
                         .browserUrl("https://pqdunittest.atlassian.net/issues/?jql=Sprint%3D4")
                         .issues(List.of(generateJiraIssue()))
                         .build();
    }

    private static JiraIssue generateJiraIssue() {
        return JiraIssue.builder()
                        .issueId(1001L)
                        .key("PT-1")
                        .browserUrl("https://pqdunittest.atlassian.net/browse/PT-1")
                        .id(null)
                        .fields(JiraIssueFields.builder()
                                               .issueType(JiraIssueType.builder()
                                                                       .description("issue description")
                                                                       .issueId(1001L)
                                                                       .iconUrl(
                                                                               "https://pqdunittest.atlassian.net/blablabla/icon")
                                                                       .name("issue name")
                                                                       .build())
                                               .build())
                        .build();
    }

    public static ReleaseInfoEntity generateReleaseInfoEntity2() {
        return ReleaseInfoEntity.builder()
                                .sonarqubeReleaseInfoEntity(generateReleaseInfoSonarqubeEntity())
                                .productId(1L)
                                .qualityLevel(0.5)
                                .created(LocalDateTime.of(LocalDate.of(2021, 1, 7),
                                                          LocalTime.of(16, 30, 23)))
                                .build();
    }

    public static ReleaseInfo generateReleaseInfo2() {
        return ReleaseInfo.builder()
                          .releaseInfoSonarqube(Optional.of(generateReleaseInfoSonarqube()))
                          .releaseInfoJira(Optional.empty())
                          .productId(1L)
                          .qualityLevel(0.5)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 7),
                                                    LocalTime.of(16, 30, 23)))
                          .build();
    }

    private static ReleaseInfoSonarqube generateReleaseInfoSonarqube() {
        return ReleaseInfoSonarqube.builder()
                                   .securityRating(1.0)
                                   .securityVulnerabilities(2.0)
                                   .reliabilityRating(3.0)
                                   .reliabilityBugs(4.0)
                                   .maintainabilityRating(5.0)
                                   .maintainabilityDebt(6.0)
                                   .maintainabilitySmells(7.0)
                                   .build();
    }

    private static ReleaseInfoSonarqubeEntity generateReleaseInfoSonarqubeEntity() {
        return ReleaseInfoSonarqubeEntity.builder()
                                         .securityRating(1.0)
                                         .securityVulnerabilities(2.0)
                                         .reliabilityRating(3.0)
                                         .reliabilityBugs(4.0)
                                         .maintainabilityRating(5.0)
                                         .maintainabilityDebt(6.0)
                                         .maintainabilitySmells(7.0)
                                         .build();
    }

    public static ReleaseInfo generateReleaseInfo_withoutSonarqube() {
        return ReleaseInfo.builder()
                          .releaseInfoSonarqube(Optional.empty())
                          .releaseInfoJira(Optional.of(generateReleaseInfoJira()))
                          .productId(1L)
                          .qualityLevel(0.83)
                          .created(LocalDateTime.of(LocalDate.of(2021, 1, 3),
                                                    LocalTime.of(18, 30, 23)))
                          .build();
    }

    public static ReleaseInfoEntity generateReleaseInfoEntity_withoutSonarqube() {
        return ReleaseInfoEntity.builder()
                                .jiraSprintEntity(List.of(JiraSprintEntity.buildJiraSprintEntity(generateJiraSprint())))
                                .productId(1L)
                                .qualityLevel(0.83)
                                .created(LocalDateTime.of(LocalDate.of(2021, 1, 3),
                                                          LocalTime.of(18, 30, 23)))
                                .build();
    }
}
