package com.pqd.application.usecase.sonarqube;

import com.pqd.application.domain.connection.ConnectionResult;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;

public interface SonarqubeGateway { //REST Gateway

    ReleaseInfoSonarqube getSonarqubeReleaseInfo(SonarqubeInfo sonarqubeInfo);

    ConnectionResult testSonarqubeConnection(SonarqubeInfo sonarqubeInfo);
}
