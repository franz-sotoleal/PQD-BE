package com.pqd.adapters.web.product;

import com.pqd.adapters.web.product.json.SaveProductRequestJson;
import com.pqd.adapters.web.product.json.SaveProductResultJson;
import com.pqd.application.domain.claim.ClaimLevel;
import com.pqd.application.usecase.claim.SaveClaim;
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

    private final SaveClaim saveClaim;

    @PostMapping("/save")
    ResponseEntity<SaveProductResultJson> saveProduct(@RequestBody SaveProductRequestJson requestJson) {
        SaveProduct.Response savedProduct = saveProduct.execute(requestJson.toSaveProductRequest());
        saveClaim.execute(requestJson.toSaveClaimRequest(savedProduct.getProduct().getId(), ClaimLevel.builder().value(ClaimLevel.ADMIN).build()));

        SaveProductResponsePresenter presenter = new SaveProductResponsePresenter();

        presenter.present(savedProduct);

        return presenter.getViewModel();
    }

}
