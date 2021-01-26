package com.pqd.adapters.jira;

import com.pqd.adapters.jira.model.*;
import com.pqd.application.domain.connection.ConnectionResult;
import com.pqd.application.domain.jira.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * IMPORTANT NOTICE
 * Tests depend on the values that these methods return
 * For example, generateJiraInfo(), generateActiveSprintsResponse() and generateActiveSprintList() values depend on each other
 */
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

    // --- ACTIVE SPRINTS ---

    public static JiraActiveSprintResponse generateActiveSprintsResponse() {
        JiraActiveSprintResponse.JiraSprint[] sprints = new JiraActiveSprintResponse.JiraSprint[2];
        sprints[0] = new JiraActiveSprintResponse.JiraSprint(
                3L,
                "sprindi nimi",
                LocalDateTime.of(LocalDate.of(2021, 1, 18), LocalTime.of(18, 31, 9, 879)),
                LocalDateTime.of(LocalDate.of(2021, 1, 30), LocalTime.of(13, 30, 9, 879)),
                1L,
                "goal");
        sprints[1] = new JiraActiveSprintResponse.JiraSprint(
                4L,
                "sprindi nimi 2",
                LocalDateTime.of(LocalDate.of(2021, 1, 16), LocalTime.of(17, 31, 9, 879)),
                LocalDateTime.of(LocalDate.of(2021, 1, 27), LocalTime.of(14, 30, 9, 879)),
                1L,
                "goal 2");
        return new JiraActiveSprintResponse(sprints);
    }

    public static List<JiraSprint> generateActiveSprintList() {
        return List.of(generateJiraSprint(), generateJiraSprint_2());
    }

    private static JiraSprint generateJiraSprint() {
        return JiraSprint.builder()
                         .id(null)
                         .sprintId(3L)
                         .name("sprindi nimi")
                         .goal("goal")
                         .boardId(1L)
                         .start(LocalDateTime.of(LocalDate.of(2021, 1, 18), LocalTime.of(18, 31, 9, 879)))
                         .end(LocalDateTime.of(LocalDate.of(2021, 1, 30), LocalTime.of(13, 30, 9, 879)))
                         .browserUrl("https://pqdunittest.atlassian.net/issues/?jql=Sprint%3D3")
                         .build();
    }

    private static JiraSprint generateJiraSprint_2() {
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

    // --- SPRINT ISSUES ---

    public static JiraSprintIssuesResponse generateSprintIssuesResponse() {
        JiraIssueResponse[] issues = new JiraIssueResponse[2];
        issues[0] = new JiraIssueResponse(1001L,
                                          "PT-1",
                                          new JiraIssueFieldsResponse(
                                                  new JiraIssueTypeResponse(1001L,
                                                                            1001L,
                                                                            "issue description",
                                                                            "https://pqdunittest.atlassian.net/blablabla/icon",
                                                                            "issue name"
                                                  )));
        issues[1] = new JiraIssueResponse(1002L,
                                          "PT-2",
                                          new JiraIssueFieldsResponse(
                                                  new JiraIssueTypeResponse(1002L,
                                                                            1002L,
                                                                            "issue description 2",
                                                                            "https://pqdunittest.atlassian.net/blablabla/icon2",
                                                                            "issue name 2"
                                                  )));
        return new JiraSprintIssuesResponse(issues);
    }

    public static List<JiraIssue> generateSprintIssuesList() {
        return List.of(generateJiraIssue(), generateJiraIssue_2());

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

    private static JiraIssue generateJiraIssue_2() {
        return JiraIssue.builder()
                        .issueId(1002L)
                        .key("PT-2")
                        .browserUrl("https://pqdunittest.atlassian.net/browse/PT-2")
                        .id(null)
                        .fields(JiraIssueFields.builder()
                                               .issueType(JiraIssueType.builder()
                                                                       .description("issue description 2")
                                                                       .issueId(1002L)
                                                                       .iconUrl(
                                                                               "https://pqdunittest.atlassian.net/blablabla/icon2")
                                                                       .name("issue name 2")
                                                                       .build())
                                               .build())
                        .build();
    }

    // --- CONNECTION ---

    public static ConnectionResult generateConnectionResult_success() {
        return ConnectionResult.builder()
                               .connectionOk(true)
                               .message("Connection successful")
                               .build();
    }

    public static ConnectionResult generateConnectionResult_wrongBaseUrl() {
        return ConnectionResult.builder()
                               .connectionOk(false)
                               .message("Could not connect to Jira server: Connection refused for baseurl https://pqdunittest.atlassian.net")
                               .build();

    }

    public static ConnectionResult generateConnectionResult_wrongToken() {
        return ConnectionResult.builder()
                               .connectionOk(false)
                               .message("Connection established, but something went wrong: Connection unauthorized, probably invalid Jira API token or user email")
                               .build();
    }

    public static ConnectionResult generateConnectionResult_wrongBoard() {
        return ConnectionResult.builder()
                               .connectionOk(false)
                               .message("Connection established, but something went wrong: 404 NOT_FOUND")
                               .build();
    }
}
