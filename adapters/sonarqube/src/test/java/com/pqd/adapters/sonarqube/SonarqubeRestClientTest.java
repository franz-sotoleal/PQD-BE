package com.pqd.adapters.sonarqube;

import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SonarqubeRestClientTest {

    private RestTemplate restTemplate;
    private SonarqubeRestClient restClient;

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);
        restClient = new SonarqubeRestClient(restTemplate);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_all_good_WHEN_sonarqube_measures_requested_THEN_sonarqube_release_info_returned() {
        SonarqubeMeasureResponse generatedSqResponse = TestDataGenerator.generateSonarqubeMeasureResponse();
        ReleaseInfoSonarqube generatedReleaseInfoSonarqube = TestDataGenerator.generateReleaseInfoSonarqube();
        ResponseEntity<SonarqubeMeasureResponse> responseEntity =
                new ResponseEntity<>(generatedSqResponse, HttpStatus.OK);
        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                                   ArgumentMatchers.any(HttpMethod.class),
                                   ArgumentMatchers.any(),
                                   ArgumentMatchers.<Class<SonarqubeMeasureResponse>>any()))
                .thenReturn(responseEntity);

        ReleaseInfoSonarqube result = restClient.getSonarqubeReleaseInfo("a", "a", "a");

        assertThat(result).isEqualTo(generatedReleaseInfoSonarqube);
    }

    @Test
    void GIVEN_request_error_WHEN_sonarqube_measures_requested_THEN_sonarqube_rest_client_exception_thrown() {
        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                                   ArgumentMatchers.any(HttpMethod.class),
                                   ArgumentMatchers.any(),
                                   ArgumentMatchers.<Class<SonarqubeMeasureResponse>>any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "request error"));

        Exception exception =
                assertThrows(Exception.class, () -> restClient.getSonarqubeReleaseInfo("a","a","a"));
        assertThat(exception).hasStackTraceContaining("SonarqubeRestClientException");
    }

    @Test
    void GIVEN_metric_not_found_WHEN_sonarqube_measures_requested_THEN_sonarqube_measure_response_exception_thrown() {
        SonarqubeMeasureResponse generatedSqResponse = TestDataGenerator.generateSonarqubeMeasureResponse_withInvalidMeasures();
        ResponseEntity<SonarqubeMeasureResponse> responseEntity =
                new ResponseEntity<>(generatedSqResponse, HttpStatus.OK);
        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                                   ArgumentMatchers.any(HttpMethod.class),
                                   ArgumentMatchers.any(),
                                   ArgumentMatchers.<Class<SonarqubeMeasureResponse>>any()))
                .thenReturn(responseEntity);

        Exception exception =
                assertThrows(Exception.class, () -> restClient.getSonarqubeReleaseInfo("a","a","a"));
        assertThat(exception).hasStackTraceContaining("SonarqubeMeasureResponseException");
    }

}
