package com.pqd.adapters.web.product.presenter;

import com.pqd.adapters.web.ResponsePresenter;
import com.pqd.adapters.web.product.json.ConnectionResultJson;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.sonarqube.TestSonarqubeConnection;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

public class ConnectionTestPresenter implements ResponsePresenter {

    private ConnectionResultJson result;

    @Override
    public void present(AbstractResponse response) {
        var connectionResult = ((TestSonarqubeConnection.Response) response).getConnectionResult();
        result = ConnectionResultJson.builder()
                                     .connectionOk(connectionResult.isConnectionOk())
                                     .message(connectionResult.getMessage())
                                     .build();
    }

    @Override
    public ResponseEntity<ConnectionResultJson> getViewModel() {
        Assert.notNull(result, () -> "Result must be presented");
        return ResponseEntity.ok(result);
    }
}
