package com.pqd.adapters.web.product;

import com.pqd.adapters.web.ResponsePresenter;
import com.pqd.adapters.web.product.json.SaveProductResultJson;
import com.pqd.adapters.web.product.json.SonarqubeInfoResultJson;
import com.pqd.application.domain.product.Product;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.product.SaveProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

public class SaveProductResponsePresenter implements ResponsePresenter {

    private SaveProductResultJson result;

    @Override
    public void present(AbstractResponse response) {
        Product product = ((SaveProduct.Response) response).getProduct();
        result = SaveProductResultJson
                .builder()
                .id(product.getId())
                .name(product.getName())
                .token(product.getToken())
                .sonarqubeInfo(SonarqubeInfoResultJson
                                       .builder()
                                       .baseUrl(product.getSonarqubeInfo().getBaseUrl())
                                       .componentName(product.getSonarqubeInfo().getComponentName())
                                       .token(product.getSonarqubeInfo().getToken())
                                       .build())
                .build();
    }

    public ResponseEntity<SaveProductResultJson> getViewModel() {
        Assert.notNull(result, () -> "Result must be presented");
        return ResponseEntity.ok(result);
    }
}
