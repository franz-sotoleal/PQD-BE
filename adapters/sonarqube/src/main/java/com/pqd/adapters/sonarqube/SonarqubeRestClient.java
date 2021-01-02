package com.pqd.adapters.sonarqube;

import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.usecase.sonarqube.SonarqubeGateway;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Objects;

@Component
@AllArgsConstructor
public class SonarqubeRestClient implements SonarqubeGateway {

    private final RestTemplate sonarqubeApiRestTemplate;

    @Override
    public ReleaseInfoSonarqube getSonarqubeReleaseInfo(String baseUrl, String componentName, String token) {
        String tokenBase = token + ":";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));
        String uri = baseUrl + "/api/measures/component?component=" + componentName + "&metricKeys=security_rating,vulnerabilities,reliability_rating,bugs,sqale_rating,sqale_index,code_smells";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, basicAuth);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SonarqubeMeasureResponse> response =
                sonarqubeApiRestTemplate.exchange(uri, HttpMethod.GET, entity, SonarqubeMeasureResponse.class);

        return ReleaseInfoSonarqube.builder()
                                   .securityRating(Objects.requireNonNull(response.getBody()).getMetricValue("security_rating"))
                                   .reliabilityRating(Objects.requireNonNull(response.getBody()).getMetricValue("reliability_rating"))
                                   .maintainabilityRating(Objects.requireNonNull(response.getBody()).getMetricValue("sqale_rating"))
                                   .maintainabilityDebt(Objects.requireNonNull(response.getBody()).getMetricValue("sqale_index"))
                                   .maintainabilitySmells(Objects.requireNonNull(response.getBody()).getMetricValue("code_smells"))
                                   .securityVulnerabilities(Objects.requireNonNull(response.getBody()).getMetricValue("vulnerabilities"))
                                   .reliabilityBugs(Objects.requireNonNull(response.getBody()).getMetricValue("bugs"))
                                   .build();
    }
}
