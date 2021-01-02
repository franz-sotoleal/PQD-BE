package com.pqd.application.usecase.release;

import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.domain.sonarqube.SonarqubeReleaseInfo;
import com.pqd.application.usecase.UseCase;
import com.pqd.application.usecase.product.ProductGateway;
import com.pqd.application.usecase.sonarqube.RetrieveSonarqubeData;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@UseCase
@Transactional
public class CollectAndSaveAllReleaseData {

    private final ProductGateway productGateway;

    private final RetrieveSonarqubeData retrieveSonarqubeData;

    private final SaveReleaseInfo saveReleaseInfo;

    public void execute(Request request) {
        Product product = productGateway.findById(request.getProductId()).orElseThrow(NoSuchElementException::new);
        RetrieveSonarqubeData.Request retrieveSqDataRequest =
                RetrieveSonarqubeData.Request.of(product.getSonarqubeInfo().getBaseUrl(),
                                                 product.getSonarqubeInfo().getComponentName(),
                                                 product.getSonarqubeInfo().getToken());
        SonarqubeReleaseInfo sonarqubeReleaseInfo = retrieveSonarqubeData.execute(retrieveSqDataRequest)
                                                                         .getReleaseInfo();

        saveReleaseInfo.execute(SaveReleaseInfo.Request.of(sonarqubeReleaseInfo, request.getProductId()));
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
