package com.pqd.adapters.web.product.json;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SonarqubeInfoResultJson {

    String baseUrl;

    String componentName;

    // String token; // token for authorization on sonarqube api side
}
