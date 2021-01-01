package com.pqd.adapters.sonarqube;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Data
@RequiredArgsConstructor
public class SonarqubeMeasureResponse {

    Component component;

    @Data
    @RequiredArgsConstructor
    static class Component {

        String id;
        String key;
        String name;
        String description;
        String qualifier;
        Measure[] measures;

        @Data
        @RequiredArgsConstructor
        static class Measure {
            String metric;
            double value;
            boolean bestValue;
        }
    }

    double getMetricValue(String metricName) {
        return Arrays.stream(component.getMeasures()).filter(measure -> measure.metric.equals(metricName)).findFirst()
                      .map(measure -> measure.value).orElseThrow(NoSuchElementException::new);
    }
}
