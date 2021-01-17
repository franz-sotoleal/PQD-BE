package com.pqd.application.usecase.release;

import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.usecase.jira.RetrieveReleaseInfoJira;
import com.pqd.application.usecase.product.GetProduct;
import com.pqd.application.usecase.sonarqube.RetrieveSonarqubeData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CollectAndSaveAllReleaseDataTest {

    private SaveReleaseInfo saveReleaseInfo;
    private GetProduct getProduct;
    private CollectAndSaveAllReleaseData collectAndSaveAllReleaseData;
    private RetrieveSonarqubeData retrieveSonarqubeData;
    private RetrieveReleaseInfoJira retrieveReleaseInfoJira;

    @Captor
    private ArgumentCaptor<SaveReleaseInfo.Request> captor;

    @BeforeEach
    void setup() {
        getProduct = mock(GetProduct.class);
        saveReleaseInfo = mock(SaveReleaseInfo.class);
        retrieveSonarqubeData = mock(RetrieveSonarqubeData.class);
        retrieveReleaseInfoJira = mock(RetrieveReleaseInfoJira.class);
        collectAndSaveAllReleaseData =
                new CollectAndSaveAllReleaseData(retrieveSonarqubeData, saveReleaseInfo, getProduct, retrieveReleaseInfoJira);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_correct_request_WHEN_collect_and_save_all_release_data_executed_THEN_related_use_cases_executed() {
        CollectAndSaveAllReleaseData.Request request = TestDataGenerator.generateCollectAndSaveAllReleaseDataRequest();
        Product product = TestDataGenerator.generateProduct();
        ReleaseInfoSonarqube releaseInfoSonarqube = TestDataGenerator.generateReleaseInfoSonarqube();

        when(getProduct.execute(any())).thenReturn(GetProduct.Response.of(product));
        when(retrieveSonarqubeData
                     .execute(RetrieveSonarqubeData.Request.of(product.getSonarqubeInfo().getBaseUrl(),
                                                               product.getSonarqubeInfo().getComponentName(),
                                                               product.getSonarqubeInfo().getToken())))
                .thenReturn(RetrieveSonarqubeData.Response.of(releaseInfoSonarqube));
        when(saveReleaseInfo.execute(any())).thenReturn(
                SaveReleaseInfo.Response.of(TestDataGenerator.generateReleaseInfo()));

        collectAndSaveAllReleaseData.execute(request);

        verify(saveReleaseInfo).execute(captor.capture());
        assertThat(product.getId()).isEqualTo(captor.getValue().getProductId());
        assertThat(releaseInfoSonarqube).isEqualTo(captor.getValue().getReleaseInfoSonarqube());
    }

}
