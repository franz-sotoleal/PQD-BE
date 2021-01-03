package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RetrieveSonarqubeDataTest {

    private SonarqubeGateway sonarqubeGateway;
    private RetrieveSonarqubeData retrieveSonarqubeData;

    @BeforeEach
    void setup() {
        sonarqubeGateway = mock(SonarqubeGateway.class);
        retrieveSonarqubeData = new RetrieveSonarqubeData(sonarqubeGateway);
    }

    @Test
    void GIVEN_sq_release_info_exists_WHEN_request_executed_THEN_release_info_sonarqube_returned() {
        ReleaseInfoSonarqube releaseInfoSonarqube = TestDataGenerator.generateReleaseInfoSonarqube();
        when(sonarqubeGateway.getSonarqubeReleaseInfo("a", "a", "a")).thenReturn(releaseInfoSonarqube);

        RetrieveSonarqubeData.Response response =
                retrieveSonarqubeData.execute(RetrieveSonarqubeData.Request.of("a", "a", "a"));

        verify(sonarqubeGateway).getSonarqubeReleaseInfo("a", "a", "a");
        assertThat(response.getReleaseInfo()).isEqualTo(releaseInfoSonarqube);
    }
}
