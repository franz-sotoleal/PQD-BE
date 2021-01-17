package com.pqd.application.domain.jira;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class JiraIssue {

    private final Long id;

    private final Long issueId;

    private final String key;

    private final JiraIssueFields fields;

    private final String browserUrl;

    public static String createBrowserUrl(String baseUrl, String issueKey) {
        return String.format("%s/browse/%s", baseUrl, issueKey);
    }

}
