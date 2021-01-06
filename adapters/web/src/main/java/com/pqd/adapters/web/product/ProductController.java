package com.pqd.adapters.web.product;

import com.pqd.adapters.web.product.json.ProductResultJson;
import com.pqd.adapters.web.product.json.SaveProductRequestJson;
import com.pqd.adapters.web.security.jwt.JwtTokenUtil;
import com.pqd.adapters.web.security.jwt.JwtUserProductClaim;
import com.pqd.application.domain.claim.ClaimLevel;
import com.pqd.application.usecase.claim.SaveClaim;
import com.pqd.application.usecase.product.GetProductList;
import com.pqd.application.usecase.product.SaveProduct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/product")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {

    private final SaveProduct saveProduct;

    private final SaveClaim saveClaim;

    private final GetProductList getProductList;

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/save")
    public ResponseEntity<ProductResultJson> saveProduct(@RequestBody @NonNull SaveProductRequestJson requestJson) {
        checkRequiredFieldPresence(requestJson);
        SaveProduct.Response savedProduct = saveProduct.execute(requestJson.toSaveProductRequest());
        saveClaim.execute(requestJson.toSaveClaimRequest(savedProduct.getProduct().getId(),
                                                         ClaimLevel.builder().value(ClaimLevel.ADMIN).build()));

        ProductPresenter presenter = new ProductPresenter();
        presenter.present(savedProduct);

        return presenter.getViewModel();
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<ProductResultJson>> getUserProductList(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        List<JwtUserProductClaim> productClaimsFromToken =
                jwtTokenUtil.getProductClaimsFromToken(authorizationHeader.split(" ")[1]);
        List<Long> productIds =
                productClaimsFromToken.stream().map(JwtUserProductClaim::getProductId).collect(Collectors.toList());

        GetProductList.Response response = getProductList.execute(GetProductList.Request.of(productIds));

        ProductListPresenter presenter = new ProductListPresenter();
        presenter.present(response);

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
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
