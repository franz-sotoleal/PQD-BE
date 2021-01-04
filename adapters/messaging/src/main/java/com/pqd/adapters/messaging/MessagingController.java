package com.pqd.adapters.messaging;

import com.pqd.adapters.messaging.async.AsyncService;
import com.pqd.application.domain.product.Product;
import com.pqd.application.usecase.product.GetProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private GetProduct getProduct;

    /**
     * Runs asynchronously. Response 200 means the controller got the request and started async thread
     * Requires Basic authorization header
     * @param productId id for which product collection is triggered
     * @return HTTP status 200 if ok
     */
    @PostMapping("/trigger")
    public ResponseEntity<String> triggerReleaseInfoCollection(@RequestHeader Map<String, String> headers, @RequestParam Long productId) {
        if (!isValidToken(productId, headers.get("authorization"))) {
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



}
