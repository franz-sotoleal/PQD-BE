package com.pqd.adapters.web.product.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SaveProductResultJson {

    Long id;

    String name;

    String token; // token for authorization on requests to messaging adapter

    SonarqubeInfoResultJson sonarqubeInfo;
}
