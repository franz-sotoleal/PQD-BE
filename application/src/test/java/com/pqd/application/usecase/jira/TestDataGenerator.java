package com.pqd.application.usecase.jira;

import com.pqd.application.domain.jira.*;
import com.pqd.application.domain.release.ReleaseInfoJira;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class TestDataGenerator {
    public static JiraInfo generateJiraInfo() {
        return JiraInfo.builder()
                       .userEmail("user@mail.com")
                       .boardId(1L)
                       .id(102L)
                       .token("token123")
                       .baseUrl("https://pqdunittest.atlassian.net")
                       .build();
    }

    public static ReleaseInfoJira generateReleaseInfoJira() {
        return ReleaseInfoJira.builder()
                              .jiraSprints(List.of(generateJiraSprint()))
                              .build();
    }

    public static JiraSprint generateJiraSprint() {
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

    public static JiraSprint generateJiraSprint_withoutIssues() {
        return JiraSprint.builder()
                         .id(null)
                         .sprintId(4L)
                         .name("sprindi nimi 2")
                         .goal("goal 2")
                         .boardId(1L)
                         .start(LocalDateTime.of(LocalDate.of(2021, 1, 16), LocalTime.of(17, 31, 9, 879)))
                         .end(LocalDateTime.of(LocalDate.of(2021, 1, 27), LocalTime.of(14, 30, 9, 879)))
                         .browserUrl("https://pqdunittest.atlassian.net/issues/?jql=Sprint%3D4")
                         .build();
    }

    public static JiraIssue generateJiraIssue() {
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
}
