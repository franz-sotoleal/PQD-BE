package com.pqd.application.usecase.release;

import com.pqd.application.domain.release.ReleaseInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GetProductReleaseInfoTest {

    private GetProductReleaseInfo getProductReleaseInfo;

    private ReleaseInfoGateway gateway;

    @BeforeEach
    void setup() {
        gateway = mock(ReleaseInfoGateway.class);
        getProductReleaseInfo = new GetProductReleaseInfo(gateway);
    }

    @Test
    void GIVEN_release_info_exists_WHEN_release_info_requested_THEN_release_info_list_returned() {
        ReleaseInfo releaseInfo = TestDataGenerator.generateReleaseInfo();
        ReleaseInfo releaseInfo2 = TestDataGenerator.generateReleaseInfo2();
        List<ReleaseInfo> releaseInfoList =
                List.of(releaseInfo, releaseInfo2);
        when(gateway.findAllByProductId(any())).thenReturn(releaseInfoList);

        List<ReleaseInfo> actual =
                getProductReleaseInfo.execute(GetProductReleaseInfo.Request.of(1L)).getReleaseInfoList();

        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0)).isEqualTo(releaseInfo);
        assertThat(actual.get(1)).isEqualTo(releaseInfo2);
    }

    @Test
    void GIVEN_release_info_doesnt_exists_WHEN_release_info_requested_THEN_empty_list_returned() {
        when(gateway.findAllByProductId(any())).thenReturn(List.of());

        List<ReleaseInfo> actual =
                getProductReleaseInfo.execute(GetProductReleaseInfo.Request.of(1L)).getReleaseInfoList();

        assertThat(actual.size()).isEqualTo(0);
    }
}
