package com.pqd.adapters.sonarqube;

import com.pqd.application.domain.sonarqube.SecurityRating;
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
    public SecurityRating getSecurityRating(String baseUrl, String componentName, String token) {
        String tokenBase = "9257cc3a6b0610da1357f73e03524b090658553d" + ":";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));
        String uri = "http://localhost:9000/api/measures/component?component=ESI-builtit&metricKeys=security_rating";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, basicAuth);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SonarqubeMeasureResponse> response =
                sonarqubeApiRestTemplate.exchange(uri, HttpMethod.GET, entity, SonarqubeMeasureResponse.class);

        return SecurityRating.builder()
                     .value(Objects.requireNonNull(response.getBody()).getMetricValue("security_rating"))
                     .build();
    }

}
