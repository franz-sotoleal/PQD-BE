package com.pqd.adapters.persistence.release;

import com.pqd.application.domain.release.ReleaseInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.util.List;

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
        ReleaseInfo releaseInfo = TestDataGenerator.generateReleaseInfo_withJira();
        ReleaseInfoEntity releaseInfoEntity = TestDataGenerator.generateReleaseInfoEntity_withJira();
        when(repository.save(any())).thenReturn(releaseInfoEntity);

        ReleaseInfo actual = adapter.save(releaseInfo);

        verify(repository, times(2)).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(releaseInfoEntity);
        assertThat(actual).isEqualTo(releaseInfo);
    }

    @Test
    void GIVEN_release_info_without_jira_WHEN_saving_entity_THEN_entity_passed_and_saved() {
        ReleaseInfo releaseInfo = TestDataGenerator.generateReleaseInfo_withoutJira();
        ReleaseInfoEntity releaseInfoEntity = TestDataGenerator.generateReleaseInfoEntity();
        when(repository.save(any())).thenReturn(releaseInfoEntity);

        ReleaseInfo actual = adapter.save(releaseInfo);

        verify(repository, times(2)).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(releaseInfoEntity);
        assertThat(actual).isEqualTo(releaseInfo);
    }

    @Test
    void GIVEN_release_info_without_sonarqube_WHEN_saving_entity_THEN_entity_passed_and_saved() {
        ReleaseInfo releaseInfo = TestDataGenerator.generateReleaseInfo_withoutSonarqube();
        ReleaseInfoEntity releaseInfoEntity = TestDataGenerator.generateReleaseInfoEntity_withoutSonarqube();
        when(repository.save(any())).thenReturn(releaseInfoEntity);

        ReleaseInfo actual = adapter.save(releaseInfo);

        verify(repository, times(2)).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(releaseInfoEntity);
        assertThat(actual).isEqualTo(releaseInfo);
    }

    @Test
    void GIVEN_release_info_exist_WHEN_finding_all_release_info_by_id_THEN_release_info_list_returned() {
        ReleaseInfoEntity releaseInfoEntity = TestDataGenerator.generateReleaseInfoEntity();
        ReleaseInfoEntity releaseInfoEntity2 = TestDataGenerator.generateReleaseInfoEntity2();
        ReleaseInfo expected = TestDataGenerator.generateReleaseInfo();
        ReleaseInfo expected2 = TestDataGenerator.generateReleaseInfo2();
        when(repository.findAllByProductIdOrderByIdDesc(any()))
                .thenReturn(List.of(releaseInfoEntity, releaseInfoEntity2));

        List<ReleaseInfo> actual = adapter.findAllByProductId(1L);

        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0)).isEqualTo(expected);
        assertThat(actual.get(1)).isEqualTo(expected2);
    }

    @Test
    void GIVEN_release_info_doesnt_exist_WHEN_finding_all_release_info_by_id_THEN_empty_list_returned() {
        when(repository.findAllByProductIdOrderByIdDesc(any())).thenReturn(List.of());

        List<ReleaseInfo> actual = adapter.findAllByProductId(1L);

        assertThat(actual.size()).isEqualTo(0);
    }
}
