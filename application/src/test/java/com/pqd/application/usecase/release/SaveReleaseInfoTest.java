package com.pqd.application.usecase.release;

import com.pqd.application.domain.release.ReleaseInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SaveReleaseInfoTest {

    private SaveReleaseInfo saveReleaseInfo;
    private ReleaseInfoGateway gateway;
    private CalculateQualityLevel calculateQualityLevel;

    @Captor
    private ArgumentCaptor<ReleaseInfo> captor;

    @BeforeEach
    void setup() {
        gateway = mock(ReleaseInfoGateway.class);
        calculateQualityLevel = mock(CalculateQualityLevel.class);
        saveReleaseInfo = new SaveReleaseInfo(gateway, calculateQualityLevel);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_correct_request_WHEN_saving_release_info_THEN_release_info_saved() {
        SaveReleaseInfo.Request request = TestDataGenerator.generateSaveReleaseInfoRequest();
        SaveReleaseInfo.Response response = TestDataGenerator.generateSaveReleaseInfoResponse();
        when(gateway.save(any())).thenReturn(response.getReleaseInfo());
        when(calculateQualityLevel.execute(any()))
                .thenReturn(TestDataGenerator.generateCalculateQualityLevelResponse());

        saveReleaseInfo.execute(request);

        verify(gateway).save(captor.capture());
        assertThat(response.getReleaseInfo()).isEqualTo(captor.getValue());
    }


}
