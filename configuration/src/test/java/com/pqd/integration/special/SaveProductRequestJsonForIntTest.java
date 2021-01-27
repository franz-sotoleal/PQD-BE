package com.pqd.integration.special;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pqd.adapters.web.product.json.info.jira.JiraInfoRequestJson;
import com.pqd.adapters.web.product.json.info.sonarqube.SonarqubeInfoRequestJson;

public class SaveProductRequestJsonForIntTest {

    @JsonProperty("userId")
    public Long userId;

    @JsonProperty("name")
    public String name;

    @JsonProperty("sonarqubeInfo")
    public SonarqubeInfoRequestJson sonarqubeInfo;

    @JsonProperty("jiraInfo")
    public JiraInfoRequestJson jiraInfo;

    public SaveProductRequestJsonForIntTest(Long userId,
                                            String name,
                                            SonarqubeInfoRequestJson sonarqubeInfo,
                                            JiraInfoRequestJson jiraInfo) {
        this.userId = userId;
        this.name = name;
        this.sonarqubeInfo = sonarqubeInfo;
        this.jiraInfo = jiraInfo;
    }
}
