package com.pqd.application.domain.product;

import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Product {

    Long id;

    String name;

    SonarqubeInfo sonarqubeInfo;

}
