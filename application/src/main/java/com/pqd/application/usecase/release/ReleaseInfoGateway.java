package com.pqd.application.usecase.release;

import com.pqd.application.domain.release.ReleaseInfo;

import java.util.List;

public interface ReleaseInfoGateway {

    ReleaseInfo save(ReleaseInfo releaseInfo);

    List<ReleaseInfo> findAllByProductId(Long productId);
}
