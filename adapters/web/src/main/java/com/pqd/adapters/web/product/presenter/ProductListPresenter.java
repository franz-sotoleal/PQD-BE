package com.pqd.adapters.web.product.presenter;

import com.pqd.adapters.web.ResponsePresenter;
import com.pqd.adapters.web.product.json.info.ProductResultJson;
import com.pqd.application.domain.product.Product;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.product.GetProductList;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class ProductListPresenter implements ResponsePresenter {

    List<ProductResultJson> result;

    @Override
    public void present(AbstractResponse response) {
        List<Product> products = ((GetProductList.Response) response).getProducts();
        result = products.stream().map(ProductResultJson::buildResultJson).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<List<ProductResultJson>> getViewModel() {
        Assert.notNull(result, () -> "Result must be presented");
        return ResponseEntity.ok(result);
    }
}
