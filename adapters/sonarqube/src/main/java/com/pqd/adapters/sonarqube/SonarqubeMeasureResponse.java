package com.pqd.adapters.sonarqube;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SonarqubeMeasureResponse {

    Component component;

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Component {

        String id;
        String key;
        String name;
        String description;
        String qualifier;
        Measure[] measures;

        @Data
        @RequiredArgsConstructor
        @AllArgsConstructor
        public static class Measure {
            String metric;
            double value;
            boolean bestValue;
        }
    }

    double getMetricValue(String metricName) {
        return Arrays.stream(component.getMeasures()).filter(measure -> measure.metric.equals(metricName)).findFirst()
                      .map(measure -> measure.value).orElseThrow(() -> new SonarqubeMeasureResponseException(String.format("%s metric not found in response", metricName)));
    }

    public static class SonarqubeMeasureResponseException extends RuntimeException {
        public SonarqubeMeasureResponseException(String message) {
            super(message);
        }
    }
}
