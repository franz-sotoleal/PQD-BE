package com.pqd.application.domain.jira;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class JiraInfo {

    Long id;

    String baseUrl;

    Long boardId;

    String token;

    String userEmail;
}
