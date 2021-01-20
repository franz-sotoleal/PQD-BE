package com.pqd.adapters.web.product.json.info;

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

    public static ProductResultJson buildResultJson(Product product) {
        return ProductResultJson.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .token(product.getToken())
                                .sonarqubeInfo(SonarqubeInfoResultJson
                                                       .builder()
                                                       .baseUrl(product.getSonarqubeInfo().getBaseUrl())
                                                       .componentName(product.getSonarqubeInfo().getComponentName())
                                                       .token(product.getSonarqubeInfo().getToken())
                                                       .build())
                                .jiraInfo(JiraInfoResultJson.builder()
                                                            .baseUrl(product.getJiraInfo().getBaseUrl())
                                                            .boardId(product.getJiraInfo().getBoardId())
                                                            .userEmail(product.getJiraInfo().getUserEmail())
                                                            .token(product.getJiraInfo().getToken())
                                                            .build())
                                .build();
    }
}
