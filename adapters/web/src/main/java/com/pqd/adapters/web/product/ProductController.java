package com.pqd.adapters.web.product;

import com.pqd.adapters.web.product.json.SaveProductRequestJson;
import com.pqd.adapters.web.product.json.SaveProductResultJson;
import com.pqd.application.domain.claim.ClaimLevel;
import com.pqd.application.usecase.claim.SaveClaim;
import com.pqd.application.usecase.product.SaveProduct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("api/product")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {

    private final SaveProduct saveProduct;

    private final SaveClaim saveClaim;

    @PostMapping("/save")
    ResponseEntity<SaveProductResultJson> saveProduct(@RequestBody @NonNull SaveProductRequestJson requestJson) {
        checkRequiredFieldPresence(requestJson);
        SaveProduct.Response savedProduct = saveProduct.execute(requestJson.toSaveProductRequest());
        saveClaim.execute(requestJson.toSaveClaimRequest(savedProduct.getProduct().getId(),
                                                         ClaimLevel.builder().value(ClaimLevel.ADMIN).build()));

        SaveProductResponsePresenter presenter = new SaveProductResponsePresenter();
        presenter.present(savedProduct);

        return presenter.getViewModel();
    }

    // While Sonarqube is the only supported product then SqInfo is required when saving product
    private void checkRequiredFieldPresence(SaveProductRequestJson requestJson) {
        if (requestJson.getUserId() == null
            || requestJson.getSonarqubeInfo() == null
            || requestJson.getSonarqubeInfo().getBaseUrl() == null
            || requestJson.getSonarqubeInfo().getComponentName() == null
            || requestJson.getSonarqubeInfo().getBaseUrl().length() == 0
            || requestJson.getSonarqubeInfo().getComponentName().length() == 0) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Required field missing, empty or wrong format");
        }
    }

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<?> handleBadCredentialsException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
