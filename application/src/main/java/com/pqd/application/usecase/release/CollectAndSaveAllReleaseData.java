package com.pqd.application.usecase.release;

import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.jira.JiraSprint;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.UseCase;
import com.pqd.application.usecase.jira.RetrieveReleaseInfoJira;
import com.pqd.application.usecase.product.GetProduct;
import com.pqd.application.usecase.sonarqube.RetrieveSonarqubeData;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@UseCase
@Transactional
public class CollectAndSaveAllReleaseData {

    private final RetrieveSonarqubeData retrieveSonarqubeData;

    private final SaveReleaseInfo saveReleaseInfo;

    private final GetProduct getProduct;

    private final RetrieveReleaseInfoJira retrieveReleaseInfoJira;

    public void execute(Request request) {
        Product product = getProduct.execute(GetProduct.Request.of(request.getProductId())).getProduct();

        ReleaseInfoSonarqube releaseInfoSonarqube = ReleaseInfoSonarqube.builder().build();

        if (product.hasValidSonarqubeInfo() && product.getSonarqubeInfo().isPresent()) {
            RetrieveSonarqubeData.Request retrieveSqDataRequest =
                    RetrieveSonarqubeData.Request.of(product.getSonarqubeInfo().get().getBaseUrl(),
                                                     product.getSonarqubeInfo().get().getComponentName(),
                                                     product.getSonarqubeInfo().get().getToken());
            releaseInfoSonarqube = retrieveSonarqubeData.execute(retrieveSqDataRequest)
                                                        .getReleaseInfo();
        }

        // TODO salvesta baasi: schema uuendatud ja nüüd vaja persistence adapteri asjad teha, siis core layer ja web adapter
        List<JiraSprint> activeSprints =
                retrieveReleaseInfoJira.execute(RetrieveReleaseInfoJira.Request.of(JiraInfo.builder()
                                                                                           .baseUrl(
                                                                                                   "https://kert944.atlassian.net")
                                                                                           .boardId(1L)
                                                                                           .token("dlNrqUp5na04fQyacxcx58EF")
                                                                                           .userEmail(
                                                                                                   "prinkkert@gmail.com")
                                                                                           .build()))
                                       .getActiveSprints();



        System.out.println(activeSprints);


        saveReleaseInfo.execute(SaveReleaseInfo.Request.of(releaseInfoSonarqube, request.getProductId()));
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Request {
        Long productId;

    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {
        ReleaseInfo releaseInfo;

    }
}
