package com.pqd.adapters.jira.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JiraIssueTypeResponse {

    Long id;

    Long issueId;

    String description;

    String iconUrl;

    String name;

}
