package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;
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
        SonarqubeInfo sonarqubeInfo = TestDataGenerator.generateSonarqubeInfo();
        when(sonarqubeGateway.getSonarqubeReleaseInfo(sonarqubeInfo)).thenReturn(releaseInfoSonarqube);

        RetrieveSonarqubeData.Response response =
                retrieveSonarqubeData.execute(RetrieveSonarqubeData.Request.of(sonarqubeInfo));

        verify(sonarqubeGateway).getSonarqubeReleaseInfo(sonarqubeInfo);
        assertThat(response.getReleaseInfo()).isEqualTo(releaseInfoSonarqube);
    }
}
