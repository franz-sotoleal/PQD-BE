package com.pqd.adapters.web.product.json.info.sonarqube;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SonarqubeInfoRequestJson {

    @JsonProperty("baseUrl")
    String baseUrl;

    @JsonProperty("componentName")
    String componentName;

    @JsonProperty("token")
    String token;

    public SonarqubeInfo toSonarqubeInfo() {
        return SonarqubeInfo.builder()
                            .baseUrl(baseUrl)
                            .componentName(componentName)
                            .token(token)
                            .build();
    }
}
