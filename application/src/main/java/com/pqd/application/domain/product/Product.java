package com.pqd.application.domain.product;

import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class Product {

    Long id;

    String name;

    String token; // token for authorization on requests to messaging adapter

    SonarqubeInfo sonarqubeInfo;

}
