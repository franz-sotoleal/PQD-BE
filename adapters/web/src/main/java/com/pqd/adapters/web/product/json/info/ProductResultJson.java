package com.pqd.adapters.web.product.json.info;

import com.pqd.adapters.web.product.json.info.jenkins.JenkinsInfoResultJson;
import com.pqd.adapters.web.product.json.info.jira.JiraInfoResultJson;
import com.pqd.adapters.web.product.json.info.sonarqube.SonarqubeInfoResultJson;
import com.pqd.application.domain.product.Product;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductResultJson {

    Long id;

    String name;

    String token; // token for authorization on requests to messaging adapter

    SonarqubeInfoResultJson sonarqubeInfo;

    JiraInfoResultJson jiraInfo;

    JenkinsInfoResultJson jenkinsInfo;

    public static ProductResultJson buildResultJson(Product product) {
        ProductResultJson resultJson = ProductResultJson.builder()
                                                        .id(product.getId())
                                                        .name(product.getName())
                                                        .token(product.getToken())
                                                        .build();

        if (product.getSonarqubeInfo().isPresent()) {
            resultJson.setSonarqubeInfo(
                    SonarqubeInfoResultJson
                            .builder()
                            .baseUrl(product.getSonarqubeInfo().get().getBaseUrl())
                            .componentName(product.getSonarqubeInfo().get().getComponentName())
                            .token(product.getSonarqubeInfo().get().getToken())
                            .build());
        }
        if (product.getJiraInfo().isPresent()) {
            resultJson.setJiraInfo(
                    JiraInfoResultJson.builder()
                                      .baseUrl(product.getJiraInfo().get().getBaseUrl())
                                      .boardId(product.getJiraInfo().get().getBoardId())
                                      .userEmail(product.getJiraInfo().get().getUserEmail())
                                      .token(product.getJiraInfo().get().getToken())
                                      .build());
        }
        if (product.getJenkinsInfo().isPresent()) {
            resultJson.setJenkinsInfo(
                    JenkinsInfoResultJson
                            .builder()
                            .baseUrl(product.getJenkinsInfo().get().getBaseUrl())
                            .username(product.getJenkinsInfo().get().getUsername())
                            .token(product.getJenkinsInfo().get().getToken())
                            .build());
        }
        return resultJson;
    }
}
