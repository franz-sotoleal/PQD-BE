package com.pqd.adapters.persistence.release;

import com.pqd.application.domain.release.ReleaseInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ReleaseInfoAdapterTest {

    private ReleaseInfoRepository repository;
    private ReleaseInfoAdapter adapter;

    @Captor
    private ArgumentCaptor<ReleaseInfoEntity> captor;

    @BeforeEach
    void setup() {
        repository = mock(ReleaseInfoRepository.class);
        adapter = new ReleaseInfoAdapter(repository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_release_info_WHEN_saving_entity_THEN_entity_passed_and_saved() {
        ReleaseInfo releaseInfo = TestDataGenerator.generateReleaseInfo();
        ReleaseInfoEntity releaseInfoEntity = TestDataGenerator.generateReleaseInfoEntity();
        when(repository.save(any())).thenReturn(releaseInfoEntity);

        ReleaseInfo actual = adapter.save(releaseInfo);

        verify(repository).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(releaseInfoEntity);
        assertThat(actual).isEqualTo(releaseInfo);
    }
}
