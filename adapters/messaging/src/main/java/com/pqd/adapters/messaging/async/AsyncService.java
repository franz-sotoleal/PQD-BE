package com.pqd.adapters.messaging.async;

import com.pqd.application.usecase.release.CollectAndSaveAllReleaseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Autowired
    private CollectAndSaveAllReleaseData collectAndSaveAllReleaseData;

    @Async("asyncExecutor")
    public void asyncExecution(Long productId) {
        collectAndSaveAllReleaseData.execute(CollectAndSaveAllReleaseData.Request.of(productId));
    }
}
