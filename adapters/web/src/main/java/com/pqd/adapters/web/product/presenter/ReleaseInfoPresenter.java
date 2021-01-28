package com.pqd.adapters.web.product.presenter;

import com.pqd.adapters.web.ResponsePresenter;
import com.pqd.adapters.web.product.json.release.ReleaseInfoResultJson;
import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.release.GetProductReleaseInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class ReleaseInfoPresenter implements ResponsePresenter {

    private List<ReleaseInfoResultJson> result;

    @Override
    public void present(AbstractResponse response) {
        List<ReleaseInfo> releaseInfoList = ((GetProductReleaseInfo.Response) response).getReleaseInfoList();
        result = releaseInfoList.stream().map(ReleaseInfoResultJson::buildResultJson).collect(Collectors.toList());
    }

    public ResponseEntity<List<ReleaseInfoResultJson>> getViewModel() {
        Assert.notNull(result, () -> "Result must be presented");
        return ResponseEntity.ok(result);
    }
}
