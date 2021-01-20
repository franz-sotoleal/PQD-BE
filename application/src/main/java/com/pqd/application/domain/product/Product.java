package com.pqd.application.domain.product;

import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Product {

    Long id;

    String name;

    String token; // token for authorization on requests to messaging adapter

    SonarqubeInfo sonarqubeInfo;

    JiraInfo jiraInfo;

    public boolean hasValidSonarqubeInfo() {
        return sonarqubeInfo != null
               && sonarqubeInfo.getBaseUrl() != null
               && sonarqubeInfo.getComponentName() != null
               && sonarqubeInfo.getBaseUrl().length() > 0
               && sonarqubeInfo.getComponentName().length() > 0;
    }

}
