package com.pqd.adapters.web.product.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SonarqubeInfoResultJson {

    String baseUrl;

    String componentName;

    // String token; // token for authorization on sonarqube api side
}
