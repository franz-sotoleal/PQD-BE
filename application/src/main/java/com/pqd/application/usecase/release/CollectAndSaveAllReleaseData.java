package com.pqd.application.usecase.release;

import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.usecase.UseCase;
import com.pqd.application.usecase.product.ProductGateway;
import com.pqd.application.usecase.sonarqube.RetrieveSonarqubeData;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class CollectAndSaveAllReleaseData {

    private final ProductGateway productGateway;

    private final RetrieveSonarqubeData retrieveSonarqubeData;

    private final SaveReleaseInfo saveReleaseInfo;

    public void execute(Request request) {
        Product product = productGateway.findById(request.getProductId())
                                        .orElseThrow(() -> new RuntimeException(String.format("Product with id %s not found", request.getProductId())));
        RetrieveSonarqubeData.Request retrieveSqDataRequest =
                RetrieveSonarqubeData.Request.of(product.getSonarqubeInfo().getBaseUrl(),
                                                 product.getSonarqubeInfo().getComponentName(),
                                                 product.getSonarqubeInfo().getToken());
        ReleaseInfoSonarqube releaseInfoSonarqube = retrieveSonarqubeData.execute(retrieveSqDataRequest)
                                                                         .getReleaseInfo();

        saveReleaseInfo.execute(SaveReleaseInfo.Request.of(releaseInfoSonarqube, request.getProductId()));
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Request {
        Long productId;

    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response {
        ReleaseInfo releaseInfo;

    }
}
