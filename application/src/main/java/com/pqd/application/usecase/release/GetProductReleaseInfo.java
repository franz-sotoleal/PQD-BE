package com.pqd.application.usecase.release;

import com.pqd.application.domain.release.ReleaseInfo;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@UseCase
@Transactional
public class GetProductReleaseInfo {

    private final ReleaseInfoGateway gateway;

    public Response execute(Request request) {
        List<ReleaseInfo> releaseInfoList = gateway.findAllByProductId(request.getProductId());
        return Response.of(releaseInfoList);
    }

    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {
        List<ReleaseInfo> releaseInfoList;
    }

    @Value(staticConstructor = "of")
    public static class Request {
        Long productId;
    }
}
