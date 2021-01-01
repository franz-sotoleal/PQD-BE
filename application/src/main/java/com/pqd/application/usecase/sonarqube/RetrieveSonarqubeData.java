package com.pqd.application.usecase.sonarqube;

import com.pqd.application.usecase.UseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class RetrieveSonarqubeData {

    private final SonarqubeGateway sonarqubeGateway;

    public void execute() {
        sonarqubeGateway.getSecurityRating("a", "a", "a");
    }

}
