package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.sonarqube.SonarqubeConnectionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TestSonarqubeConnectionTest {

    private SonarqubeGateway gateway;
    private TestSonarqubeConnection testSonarqubeConnection;

    @BeforeEach
    void setup() {
        gateway = mock(SonarqubeGateway.class);
        testSonarqubeConnection = new TestSonarqubeConnection(gateway);
    }

    @Test
    void GIVEN_request_WHEN_request_executed_THEN_response_returned() {
        SonarqubeConnectionResult connectionResult = TestDataGenerator.generateSonarqubeConnectionResult();
        when(gateway.testSonarqubeConnection(any(), any(), any())).thenReturn(connectionResult);

        SonarqubeConnectionResult actual =
                testSonarqubeConnection.execute(TestSonarqubeConnection.Request.of("a", "a", "a"))
                                       .getConnectionResult();
        assertThat(actual).isEqualTo(connectionResult);
    }
}
