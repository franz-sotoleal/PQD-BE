package com.pqd.application.domain.product;

import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Product {

    Long id;

    String name;

    String token; // token for authorization on requests to messaging adapter

    Optional<SonarqubeInfo> sonarqubeInfo;

    Optional<JiraInfo> jiraInfo;

    public boolean hasValidSonarqubeInfo() {
        return sonarqubeInfo.isPresent()
               && sonarqubeInfo.get().getBaseUrl() != null
               && sonarqubeInfo.get().getComponentName() != null
               && sonarqubeInfo.get().getBaseUrl().length() > 0
               && sonarqubeInfo.get().getComponentName().length() > 0;
    }

    public boolean hasValidJiraInfo() {
        return jiraInfo.isPresent()
               && jiraInfo.get().getBaseUrl() != null
               && jiraInfo.get().getBoardId() != null
               && jiraInfo.get().getUserEmail() != null
               && jiraInfo.get().getBaseUrl().length() > 0
               && jiraInfo.get().getUserEmail().length() > 0;
    }

}
