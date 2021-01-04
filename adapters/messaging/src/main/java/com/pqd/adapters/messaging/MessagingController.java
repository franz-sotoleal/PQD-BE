package com.pqd.adapters.messaging;

import com.pqd.adapters.messaging.async.AsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/messaging")
@CrossOrigin
@RequiredArgsConstructor
public class MessagingController {

    @Autowired
    private AsyncService asyncService;

    /**
     * Runs asynchronously. Response 200 means the controller got the request and started async thread
     * @param productId id for which product collection is triggered
     * @return HTTP status 200 if ok
     */
    @PostMapping("/trigger")
    public ResponseEntity<String> triggerReleaseInfoCollection(@RequestParam Long productId) {

        asyncService.asyncExecution(productId);

        return ResponseEntity.ok(String.format("Release info collection started for product with id %s", productId));
    }



}
