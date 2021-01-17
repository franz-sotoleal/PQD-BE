package com.pqd.application.domain.jira;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class JiraInfo {

    Long id;

    String baseUrl;

    Long boardId;

    String token;

    String userEmail;
}
