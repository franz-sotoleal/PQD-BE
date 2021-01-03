package com.pqd.application.usecase.release;

import com.pqd.application.domain.release.ReleaseInfo;

public interface ReleaseInfoGateway {

    ReleaseInfo save(ReleaseInfo releaseInfo);
}
