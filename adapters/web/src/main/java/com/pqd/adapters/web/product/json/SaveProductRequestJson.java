package com.pqd.adapters.web.product.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pqd.application.usecase.product.SaveProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveProductRequestJson {

    @JsonProperty("name")
    String name;

    @JsonProperty("sonarqubeInfo")
    SonarqubeInfoRequestJson sonarqubeInfo;

    public SaveProduct.Request toRequest() {
        return SaveProduct.Request.of(name, sonarqubeInfo.toSonarqubeInfo());
    }
}
