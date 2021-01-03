package com.pqd.application.usecase.release;

import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.usecase.product.ProductGateway;
import com.pqd.application.usecase.sonarqube.RetrieveSonarqubeData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CollectAndSaveAllReleaseDataTest {

    private SaveReleaseInfo saveReleaseInfo;
    private ProductGateway gateway;
    private CollectAndSaveAllReleaseData collectAndSaveAllReleaseData;
    private RetrieveSonarqubeData retrieveSonarqubeData;

    @Captor
    private ArgumentCaptor<SaveReleaseInfo.Request> captor;

    @BeforeEach
    void setup() {
        gateway = mock(ProductGateway.class);
        saveReleaseInfo = mock(SaveReleaseInfo.class);
        retrieveSonarqubeData = mock(RetrieveSonarqubeData.class);
        collectAndSaveAllReleaseData =
                new CollectAndSaveAllReleaseData(gateway, retrieveSonarqubeData, saveReleaseInfo);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_correct_request_WHEN_collect_and_save_all_release_data_executed_THEN_related_use_cases_executed() {
        CollectAndSaveAllReleaseData.Request request = TestDataGenerator.generateCollectAndSaveAllReleaseDataRequest();
        Product product = TestDataGenerator.generateProduct();
        ReleaseInfoSonarqube releaseInfoSonarqube = TestDataGenerator.getReleaseInfoSonarqube();

        when(gateway.findById(any())).thenReturn(Optional.of(product));
        when(retrieveSonarqubeData
                     .execute(RetrieveSonarqubeData.Request.of(product.getSonarqubeInfo().getBaseUrl(),
                                                               product.getSonarqubeInfo().getComponentName(),
                                                               product.getSonarqubeInfo().getToken())))
                .thenReturn(RetrieveSonarqubeData.Response.of(releaseInfoSonarqube));
        when(saveReleaseInfo.execute(any())).thenReturn(
                SaveReleaseInfo.Response.of(TestDataGenerator.getReleaseInfo()));

        collectAndSaveAllReleaseData.execute(request);

        verify(saveReleaseInfo).execute(captor.capture());
        assertThat(product.getId()).isEqualTo(captor.getValue().getProductId());
        assertThat(releaseInfoSonarqube).isEqualTo(captor.getValue().getReleaseInfoSonarqube());
    }

    @Test
    void GIVEN_product_doesnt_exist_WHEN_collect_and_save_all_release_data_executed_THEN_exception_thrown() {
        CollectAndSaveAllReleaseData.Request request = TestDataGenerator.generateCollectAndSaveAllReleaseDataRequest();
        Product product = TestDataGenerator.generateProduct();

        when(gateway.findById(any())).thenReturn(Optional.empty());

        Exception exception =
                assertThrows(Exception.class, () -> collectAndSaveAllReleaseData.execute(request));
        assertThat(exception).hasStackTraceContaining(String.format("Product with id %s not found", product.getId()));
    }
}
