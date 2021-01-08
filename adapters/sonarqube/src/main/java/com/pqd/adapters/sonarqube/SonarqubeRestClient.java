package com.pqd.adapters.sonarqube;

import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.domain.sonarqube.SonarqubeConnectionResult;
import com.pqd.application.usecase.sonarqube.SonarqubeGateway;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Objects;

@Component
@AllArgsConstructor
public class SonarqubeRestClient implements SonarqubeGateway {

    private final RestTemplate sonarqubeApiRestTemplate;

    @Override
    public ReleaseInfoSonarqube getSonarqubeReleaseInfo(String baseUrl, String componentName, String token) {
        ResponseEntity<SonarqubeMeasureResponse> response = makeHttpRequest(baseUrl, componentName, token);

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

    @Override
    public SonarqubeConnectionResult testSonarqubeConnection(String baseUrl, String componentName, String token) {
        SonarqubeConnectionResult sonarqubeConnectionResult = SonarqubeConnectionResult.builder()
                                                                                       .connectionOk(true)
                                                                                       .message("Connection successful")
                                                                                       .build();
        try {
            makeHttpRequest(baseUrl, componentName, token);
        } catch (SonarqubeConnectionRefusedException e) {
            sonarqubeConnectionResult.setConnectionOk(false);
            sonarqubeConnectionResult.setMessage("Could not connect to Sonarqube server: " + e.getMessage());
        } catch (SonarqubeRestClientException e) {
            sonarqubeConnectionResult.setConnectionOk(false);
            sonarqubeConnectionResult.setMessage("Connection established, but something went wrong: " + e.getMessage());
        } catch (Exception e) {
            sonarqubeConnectionResult.setConnectionOk(false);
            sonarqubeConnectionResult.setMessage(e.getMessage());
        }

        return sonarqubeConnectionResult;
    }

    private ResponseEntity<SonarqubeMeasureResponse> makeHttpRequest(String baseUrl, String componentName, String token) {
        String tokenBase = token + ":";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));
        String uri = baseUrl + "/api/measures/component?component=" + componentName + "&metricKeys=security_rating,vulnerabilities,reliability_rating,bugs,sqale_rating,sqale_index,code_smells";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, basicAuth);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SonarqubeMeasureResponse> response;

        try {
            response = sonarqubeApiRestTemplate.exchange(uri, HttpMethod.GET, entity, SonarqubeMeasureResponse.class);
        } catch (ResourceAccessException e) {
            throw new SonarqubeConnectionRefusedException(String.format("Connection refused for baseurl %s", baseUrl));
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                throw new SonarqubeRestClientException("Connection unauthorized, probably invalid Sonarqube API token");
            }
            throw new SonarqubeRestClientException(String.format("%s", exception.getMessage()));
        }
        return response;
    }

    @NoArgsConstructor
    public static class SonarqubeRestClientException extends RuntimeException {
        public SonarqubeRestClientException(String message) {
            super(message);
        }
    }

    @NoArgsConstructor
    public static class SonarqubeConnectionRefusedException extends SonarqubeRestClientException {
        public SonarqubeConnectionRefusedException(String message) {
            super(message);
        }
    }

}
