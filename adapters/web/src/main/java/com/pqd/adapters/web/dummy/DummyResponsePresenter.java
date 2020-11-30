package com.pqd.adapters.web.dummy;

import com.pqd.application.usecase.dummy.GetDummyResult;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

public class DummyResponsePresenter {

    private DummyResponseResultJson result;

    public void present(GetDummyResult.Response response) {
        result = new DummyResponseResultJson(response.getMessage());
    }

    ResponseEntity<DummyResponseResultJson> getViewModel() {
        Assert.notNull(result, () -> "Result must be presented");
        return ResponseEntity.ok(result);
    }
}
