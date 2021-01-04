package com.pqd.adapters.messaging;

import com.pqd.adapters.messaging.async.AsyncService;
import com.pqd.application.domain.product.Product;
import com.pqd.application.usecase.product.GetProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("api/messaging")
@CrossOrigin
@RequiredArgsConstructor
public class MessagingController {

    private final AsyncService asyncService;

    private final GetProduct getProduct;

    /**
     * Runs asynchronously. Response 200 means the controller got the request and started async thread
     * Requires Basic authorization header
     * @param productId id for which product collection is triggered
     * @return HTTP status 200 if ok
     */
    @PostMapping("/trigger")
    public ResponseEntity<String> triggerReleaseInfoCollection(@RequestHeader Map<String, String> headers, @RequestParam Long productId) {
        if (!isValidToken(productId, getAuthorizationHeader(headers))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        asyncService.asyncExecution(productId);

        return ResponseEntity.ok(String.format("Release info collection started for product with id %s", productId));
    }

    private boolean isValidToken(Long productId, String authorizationHeader) {
        String authorizationToken = authorizationHeader.split(" ")[1];
        String decodedString = new String(Base64.getDecoder().decode(authorizationToken));
        Product product = getProduct.execute(GetProduct.Request.of(productId)).getProduct();
        return decodedString.equals(product.getToken() + ":");
    }

    private String getAuthorizationHeader(Map<String, String> headers) {
        String startingUppercase = headers.get(HttpHeaders.AUTHORIZATION);
        String lowercase = headers.get(HttpHeaders.AUTHORIZATION.toLowerCase());
        String uppercase = headers.get(HttpHeaders.AUTHORIZATION.toUpperCase());

        if (startingUppercase != null) {
            return startingUppercase;
        } else if (lowercase != null) {
            return lowercase;
        } else if (uppercase != null) {
            return uppercase;
        } else {
            throw new RuntimeException("Exception with authorization header");
        }
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
