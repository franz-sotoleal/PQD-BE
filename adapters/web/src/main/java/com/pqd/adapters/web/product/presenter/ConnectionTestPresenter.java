package com.pqd.adapters.web.product.presenter;

import com.pqd.adapters.web.ResponsePresenter;
import com.pqd.adapters.web.product.json.ConnectionResultJson;
import com.pqd.application.domain.connection.ConnectionResponse;
import com.pqd.application.usecase.AbstractResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

public class ConnectionTestPresenter implements ResponsePresenter {

    private ConnectionResultJson result;

    @Override
    public void present(AbstractResponse response) {
        var connectionResult = ((ConnectionResponse) response).getConnectionResult();
        result = ConnectionResultJson.builder()
                                     .connectionOk(connectionResult.isConnectionOk())
                                     .message(connectionResult.getMessage())
                                     .build();
    }

    @Override
    public ResponseEntity<ConnectionResultJson> getViewModel() {
        Assert.notNull(result, () -> "Result must be presented");
        return result.connectionOk ? ResponseEntity.ok(result) : ResponseEntity.unprocessableEntity().body(result);
    }
}
