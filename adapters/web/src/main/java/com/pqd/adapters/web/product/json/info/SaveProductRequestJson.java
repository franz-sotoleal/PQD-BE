package com.pqd.adapters.web.product.json.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pqd.adapters.web.product.json.info.jira.JiraInfoRequestJson;
import com.pqd.adapters.web.product.json.info.sonarqube.SonarqubeInfoRequestJson;
import com.pqd.application.domain.claim.ClaimLevel;
import com.pqd.application.usecase.claim.SaveClaim;
import com.pqd.application.usecase.product.SaveProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveProductRequestJson {

    @JsonProperty("userId")
    Long userId;

    @JsonProperty("name")
    String name;

    @JsonProperty("sonarqubeInfo")
    @Builder.Default
    Optional<SonarqubeInfoRequestJson> sonarqubeInfo = Optional.empty();

    @JsonProperty("jiraInfo")
    @Builder.Default
    Optional<JiraInfoRequestJson> jiraInfo = Optional.empty();

    public SaveProduct.Request toSaveProductRequest() {
        return SaveProduct.Request.of(name,
                                      sonarqubeInfo.map(SonarqubeInfoRequestJson::toSonarqubeInfo),
                                      jiraInfo.map(JiraInfoRequestJson::toJiraInfo));
    }

    public SaveClaim.Request toSaveClaimRequest(Long productId, ClaimLevel claimLevel) {
        return SaveClaim.Request.of(userId, productId, claimLevel);
    }
}
