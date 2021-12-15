package com.pqd.application.domain.product;

import com.pqd.application.domain.jenkins.JenkinsInfo;
import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Optional;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class Product {

    Long id;

    String name;

    String token; // token for authorization on requests to messaging adapter

    Optional<SonarqubeInfo> sonarqubeInfo;

    Optional<JiraInfo> jiraInfo;

    Optional<JenkinsInfo> jenkinsInfo;

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

    public boolean hasValidJenkinsInfo() {
        return jenkinsInfo.isPresent()
               && jenkinsInfo.get().getBaseUrl() != null
               && jenkinsInfo.get().getUsername() != null
               && jenkinsInfo.get().getToken() != null
               && jenkinsInfo.get().getBaseUrl().length() > 0
               && jenkinsInfo.get().getUsername().length() > 0
               && jenkinsInfo.get().getToken().length() > 0;
    }

}
