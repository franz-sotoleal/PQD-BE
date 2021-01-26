package com.pqd.adapters.sonarqube;

import com.pqd.application.domain.connection.ConnectionResult;
import com.pqd.application.domain.release.ReleaseInfoSonarqube;

public class TestDataGenerator {

    public static ConnectionResult generateSonarqubeConnectionResult_success() {
        return ConnectionResult.builder()
                               .connectionOk(true)
                               .message("Connection successful")
                               .build();
    }

    public static ConnectionResult generateSonarqubeConnectionResult_wrongBaseUrl() {
        return ConnectionResult.builder()
                               .connectionOk(false)
                               .message("Could not connect to Sonarqube server: Connection refused for baseurl a")
                               .build();
    }

    public static ConnectionResult generateSonarqubeConnectionResult_wrongComponent() {
        return ConnectionResult.builder()
                               .connectionOk(false)
                               .message("Connection established, but something went wrong: 404 NOT_FOUND")
                               .build();
    }

    public static ConnectionResult generateSonarqubeConnectionResult_wrongToken() {
        return ConnectionResult.builder()
                               .connectionOk(false)
                               .message("Connection established, but something went wrong: Connection unauthorized, probably invalid Sonarqube API token")
                               .build();
    }

    public static SonarqubeMeasureResponse generateSonarqubeMeasureResponse() {
        return new SonarqubeMeasureResponse(
                new SonarqubeMeasureResponse.Component("id-xyz",
                                                       "abcdefg",
                                                       "Test-name",
                                                       "Test-description",
                                                       "Test-qualifier", generateMeasures()));
    }

    public static SonarqubeMeasureResponse generateSonarqubeMeasureResponse_withInvalidMeasures() {
        return new SonarqubeMeasureResponse(
                new SonarqubeMeasureResponse.Component("id-xyz",
                                                       "abcdefg",
                                                       "Test-name",
                                                       "Test-description",
                                                       "Test-qualifier", generateInvalidMeasures()));
    }

    public static ReleaseInfoSonarqube generateReleaseInfoSonarqube() {
        return ReleaseInfoSonarqube.builder()
                                   .securityRating(1.0)
                                   .securityVulnerabilities(2.0)
                                   .reliabilityRating(3.0)
                                   .reliabilityBugs(4.0)
                                   .maintainabilityRating(5.0)
                                   .maintainabilityDebt(6.0)
                                   .maintainabilitySmells(7.0)
                                   .build();
    }

    private static SonarqubeMeasureResponse.Component.Measure[] generateInvalidMeasures() {
        SonarqubeMeasureResponse.Component.Measure[] measures = new SonarqubeMeasureResponse.Component.Measure[7];
        measures[0] = new SonarqubeMeasureResponse.Component.Measure("security_rating", 1.0, false);
        measures[1] = new SonarqubeMeasureResponse.Component.Measure("INVALID", 2.0, false);
        measures[2] = new SonarqubeMeasureResponse.Component.Measure("reliability_rating", 3.0, false);
        measures[3] = new SonarqubeMeasureResponse.Component.Measure("bugs", 4.0, false);
        measures[4] = new SonarqubeMeasureResponse.Component.Measure("sqale_rating", 5.0, false);
        measures[5] = new SonarqubeMeasureResponse.Component.Measure("sqale_index", 6.0, false);
        measures[6] = new SonarqubeMeasureResponse.Component.Measure("code_smells", 7.0, false);

        return measures;
    }

    private static SonarqubeMeasureResponse.Component.Measure[] generateMeasures() {
        SonarqubeMeasureResponse.Component.Measure[] measures = new SonarqubeMeasureResponse.Component.Measure[7];
        measures[0] = new SonarqubeMeasureResponse.Component.Measure("security_rating", 1.0, false);
        measures[1] = new SonarqubeMeasureResponse.Component.Measure("vulnerabilities", 2.0, false);
        measures[2] = new SonarqubeMeasureResponse.Component.Measure("reliability_rating", 3.0, false);
        measures[3] = new SonarqubeMeasureResponse.Component.Measure("bugs", 4.0, false);
        measures[4] = new SonarqubeMeasureResponse.Component.Measure("sqale_rating", 5.0, false);
        measures[5] = new SonarqubeMeasureResponse.Component.Measure("sqale_index", 6.0, false);
        measures[6] = new SonarqubeMeasureResponse.Component.Measure("code_smells", 7.0, false);

        return measures;
    }
}
