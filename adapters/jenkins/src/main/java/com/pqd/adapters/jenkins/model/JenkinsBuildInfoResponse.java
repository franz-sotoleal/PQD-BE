package com.pqd.adapters.jenkins.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JenkinsBuildInfoResponse {

    String description;
    String name;
    Build[] builds;
    String color;
    HealthReport[] healthReport;
    Build lastBuild;
    Build lastSuccessfulBuild;
    Build lastFailedBuild;

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Build {
        boolean building;

        int number;

        String result;

        long timestamp;

        double duration;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class HealthReport {
        String description;
        int score;
    }

    public int getDeploymentFrequency() {
        return builds.length;
    }

    public double getLeadTimeForChange(){
        return Arrays.stream(builds).mapToDouble(build -> build.duration).average().orElse(Double.NaN);
    }

    public Long getTimeToRestoreService(){
        if(lastSuccessfulBuild != null  && lastFailedBuild != null){
            int previousBuildNumber = lastFailedBuild.number + 1;
            Build previousBuild = Arrays.stream(builds).filter(build -> build.number == previousBuildNumber).findFirst().get();
            return Math.abs(lastFailedBuild.timestamp - previousBuild.timestamp);
        }
        return null;
    }

    public double getChangeFailureRate() {
        double total = builds.length;
        double failedBuildsCount = (double) Arrays.stream(builds).filter(build -> build.result.equals("FAILURE")).count();
        return failedBuildsCount / total;
    }
}
