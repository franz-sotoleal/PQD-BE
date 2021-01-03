package com.pqd.application.usecase.release;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculateQualityLevelTest {

    private CalculateQualityLevel calculateQualityLevel;

    @BeforeEach
    void setup() {
        calculateQualityLevel = new CalculateQualityLevel();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_release_info_WHEN_calculate_quality_level_executed_THEN_quality_level_returned() {
        CalculateQualityLevel.Request request = TestDataGenerator.generateCalculateQualityLevelRequest();
        CalculateQualityLevel.Response actual = calculateQualityLevel.execute(request);

        assertThat(actual.getQualityLevel()).isEqualTo(0.5);
    }
}
