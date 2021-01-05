package com.pqd.adapters.web.product;

import com.pqd.adapters.web.product.json.SaveProductRequestJson;
import com.pqd.adapters.web.product.json.SaveProductResultJson;
import com.pqd.application.usecase.product.SaveProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/product")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {

    private final SaveProduct saveProduct;

    @PostMapping("/save")
    ResponseEntity<SaveProductResultJson> saveProduct(@RequestBody SaveProductRequestJson requestJson) {
        SaveProduct.Response response = saveProduct.execute(requestJson.toRequest());
        SaveProductResponsePresenter presenter = new SaveProductResponsePresenter();

        presenter.present(response);

        return presenter.getViewModel();
    }

}
