package com.pqd.adapters.web.product;

import com.pqd.adapters.web.ResponsePresenter;
import com.pqd.adapters.web.product.json.ProductResultJson;
import com.pqd.application.domain.product.Product;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.product.SaveProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

public class ProductPresenter implements ResponsePresenter {

    private ProductResultJson result;

    @Override
    public void present(AbstractResponse response) {
        Product product = ((SaveProduct.Response) response).getProduct();
        result = ProductResultJson.buildResultJson(product);
    }

    public ResponseEntity<ProductResultJson> getViewModel() {
        Assert.notNull(result, () -> "Result must be presented");
        return ResponseEntity.ok(result);
    }

}
