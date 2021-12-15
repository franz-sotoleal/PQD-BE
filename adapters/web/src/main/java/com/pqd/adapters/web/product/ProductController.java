package com.pqd.adapters.web.product;

import com.pqd.adapters.web.common.ExceptionResponseJson;
import com.pqd.adapters.web.product.json.ConnectionResultJson;
import com.pqd.adapters.web.product.json.info.ProductResultJson;
import com.pqd.adapters.web.product.json.info.SaveProductRequestJson;
import com.pqd.adapters.web.product.json.info.UpdateProductRequestJson;
import com.pqd.adapters.web.product.json.info.jenkins.JenkinsInfoRequestJson;
import com.pqd.adapters.web.product.json.info.jira.JiraInfoRequestJson;
import com.pqd.adapters.web.product.json.info.sonarqube.SonarqubeInfoRequestJson;
import com.pqd.adapters.web.product.json.release.ReleaseInfoResultJson;
import com.pqd.adapters.web.product.presenter.ConnectionTestPresenter;
import com.pqd.adapters.web.product.presenter.ProductListPresenter;
import com.pqd.adapters.web.product.presenter.ProductPresenter;
import com.pqd.adapters.web.product.presenter.ReleaseInfoPresenter;
import com.pqd.adapters.web.security.jwt.JwtTokenUtil;
import com.pqd.adapters.web.security.jwt.JwtUserProductClaim;
import com.pqd.application.domain.claim.ClaimLevel;
import com.pqd.application.domain.jenkins.JenkinsInfo;
import com.pqd.application.usecase.claim.SaveClaim;
import com.pqd.application.usecase.jenkins.TestJenkinsConnection;
import com.pqd.application.usecase.jira.TestJiraConnection;
import com.pqd.application.usecase.product.DeleteProduct;
import com.pqd.application.usecase.product.GetProductList;
import com.pqd.application.usecase.product.SaveProduct;
import com.pqd.application.usecase.product.UpdateProduct;
import com.pqd.application.usecase.release.GetProductReleaseInfo;
import com.pqd.application.usecase.sonarqube.TestSonarqubeConnection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ProductController {

    private final SaveProduct saveProduct;

    private final UpdateProduct updateProduct;

    private final DeleteProduct deleteProduct;

    private final SaveClaim saveClaim;

    private final GetProductList getProductList;

    private final JwtTokenUtil jwtTokenUtil;

    private final GetProductReleaseInfo getProductReleaseInfo;

    private final TestSonarqubeConnection testSonarqubeConnection;

    private final TestJiraConnection testJiraConnection;

    private final TestJenkinsConnection testJenkinsConnection;

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

    @PutMapping("/{productId}/update")
    public ResponseEntity<ProductResultJson> updateProduct(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody @NonNull UpdateProductRequestJson requestJson,
            @PathVariable(value = "productId") Long productId) {
        checkAuthority(authorizationHeader, productId);
        checkRequiredFieldPresence(requestJson);

        var response = updateProduct.execute(requestJson.toUpdateProductRequest(productId));

        ProductPresenter presenter = new ProductPresenter();
        presenter.present(SaveProduct.Response.of(response.getProduct()));

        return presenter.getViewModel();
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<String> deleteProduct(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable(value = "productId") Long productId) {
        checkAuthority(authorizationHeader, productId);

        deleteProduct.execute(DeleteProduct.Request.of(productId));

        return ResponseEntity.ok(String.format("Product with id %s deleted", productId));
    }

    @PostMapping("/test/sonarqube/connection")
    public ResponseEntity<ConnectionResultJson> testSonarqubeConnection(
            @RequestBody @NonNull SonarqubeInfoRequestJson request) {
        checkRequiredFieldPresence(request);
        var response = testSonarqubeConnection.execute(TestSonarqubeConnection.Request.of(request.toSonarqubeInfo()));

        var presenter = new ConnectionTestPresenter();
        presenter.present(response);

        return presenter.getViewModel();
    }

    @PostMapping("/test/jira/connection")
    public ResponseEntity<ConnectionResultJson> testJiraConnection(
            @RequestBody @NonNull JiraInfoRequestJson request) {
        checkRequiredFieldPresence(request);
        var response = testJiraConnection.execute(TestJiraConnection.Request.of(request.toJiraInfo()));

        var presenter = new ConnectionTestPresenter();
        presenter.present(response);

        return presenter.getViewModel();
    }

    @PostMapping("/test/jenkins/connection")
    public ResponseEntity<ConnectionResultJson> testJenkinsConnection(
            @RequestBody @NonNull JenkinsInfoRequestJson request) {
        checkRequiredFieldPresence(request);
        var response = testJenkinsConnection.execute(TestJenkinsConnection.Request.of(request.toJenkinsInfo()));

        var presenter = new ConnectionTestPresenter();
        presenter.present(response);

        return presenter.getViewModel();
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<ProductResultJson>> getUserProductList(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        List<Long> productIds = getClaimedProductIds(authorizationHeader);

        GetProductList.Response response = getProductList.execute(GetProductList.Request.of(productIds));

        ProductListPresenter presenter = new ProductListPresenter();
        presenter.present(response);

        return presenter.getViewModel();
    }

    @GetMapping("/{productId}/releaseInfo")
    public ResponseEntity<List<ReleaseInfoResultJson>> getProductReleaseInfo(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable(value = "productId") Long productId) {
        checkAuthority(authorizationHeader, productId);

        var response = getProductReleaseInfo.execute(GetProductReleaseInfo.Request.of(productId));

        ReleaseInfoPresenter presenter = new ReleaseInfoPresenter();
        presenter.present(response);

        return presenter.getViewModel();
    }

    private void checkAuthority(@RequestHeader(HttpHeaders.AUTHORIZATION)
                                        String authorizationHeader,
                                @PathVariable("productId") Long productId) {
        List<Long> productIds = getClaimedProductIds(authorizationHeader);
        checkClaimForAskedProduct(productId, productIds);
    }

    private List<Long> getClaimedProductIds(String authorizationHeader) {
        List<JwtUserProductClaim> productClaimsFromToken =
                jwtTokenUtil.getProductClaimsFromToken(authorizationHeader.split(" ")[1]);
        return productClaimsFromToken.stream().map(JwtUserProductClaim::getProductId).collect(Collectors.toList());
    }

    private void checkClaimForAskedProduct(Long askedProductId, List<Long> claimedProductsFromJwt) {
        if (!claimedProductsFromJwt.contains(askedProductId)) {
            throw new NoClaimsException("The product does not exist or you don't have access rights");
        }
    }

    private void checkRequiredFieldPresence(SaveProductRequestJson requestJson) {
        Optional<SonarqubeInfoRequestJson> sonarqubeInfo = requestJson.getSonarqubeInfo();
        Optional<JiraInfoRequestJson> jiraInfo = requestJson.getJiraInfo();
        Optional<JenkinsInfoRequestJson> jenkinsInfo = requestJson.getJenkinsInfo();
        if (requestJson.getUserId() == null
            || sonarqubeInfo.isEmpty() && jiraInfo.isEmpty() && jenkinsInfo.isEmpty()
            || sonarqubeInfo.isPresent() && !areSonarqubeFieldsPresent(sonarqubeInfo.get())
            || jiraInfo.isPresent() && !areJiraFieldsPresent(jiraInfo.get())
            || jenkinsInfo.isPresent() && !areJenkinsFieldsPresent(jenkinsInfo.get())
        ) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Required field missing, empty or wrong format");
        }
    }

    private void checkRequiredFieldPresence(UpdateProductRequestJson requestJson) {
        SonarqubeInfoRequestJson sonarqubeInfo = requestJson.getProduct().getSonarqubeInfo();
        if (requestJson.getProduct() == null
            || requestJson.getProduct().getName() == null
            || requestJson.getProduct().getName().length() == 0
            || sonarqubeInfo != null && !areSonarqubeFieldsPresent(sonarqubeInfo)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Required field missing, empty or wrong format");
        }
    }

    private void checkRequiredFieldPresence(SonarqubeInfoRequestJson requestJson) {
        if (!areSonarqubeFieldsPresent(requestJson)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Required field missing, empty or wrong format");
        }
    }

    private void checkRequiredFieldPresence(JiraInfoRequestJson requestJson) {
        if (!areJiraFieldsPresent(requestJson)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Required field missing, empty or wrong format");
        }
    }

    private void checkRequiredFieldPresence(JenkinsInfoRequestJson requestJson) {
        if (!areJenkinsFieldsPresent(requestJson)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Required field missing, empty or wrong format");
        }
    }

    private boolean areSonarqubeFieldsPresent(SonarqubeInfoRequestJson requestJson) {
        return requestJson != null
               && requestJson.getBaseUrl() != null
               && requestJson.getComponentName() != null
               && requestJson.getBaseUrl().length() != 0
               && requestJson.getComponentName().length() != 0;
    }

    private boolean areJiraFieldsPresent(JiraInfoRequestJson requestJson) {
        return requestJson != null
               && requestJson.getBaseUrl() != null
               && requestJson.getBoardId() != null
               && requestJson.getUserEmail() != null
               && requestJson.getBaseUrl().length() != 0
               && requestJson.getUserEmail().length() != 0;
    }

    private boolean areJenkinsFieldsPresent(JenkinsInfoRequestJson requestJson) {
        return requestJson != null
               && requestJson.getBaseUrl() != null
               && requestJson.getUsername() != null
               && requestJson.getToken() != null
               && requestJson.getBaseUrl().length() != 0
               && requestJson.getUsername().length() != 0
               && requestJson.getToken().length() != 0;
    }

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(ExceptionResponseJson.builder().message(e.getMessage()).build());
    }

    static class NoClaimsException extends HttpClientErrorException {
        public NoClaimsException(String message) {
            super(HttpStatus.UNAUTHORIZED, message);
        }
    }

}
