package com.pqd.adapters.jira.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JiraIssueResponse {

    Long id;

    String key;

    JiraIssueFieldsResponse fields;
}
