package com.pqd.adapters.jenkins;

import com.pqd.adapters.jenkins.model.JenkinsBuildInfoResponse;
import com.pqd.adapters.jenkins.model.JenkinsJobResponse;
import com.pqd.application.domain.connection.ConnectionResult;
import com.pqd.application.domain.jenkins.JenkinsBuild;
import com.pqd.application.domain.jenkins.JenkinsInfo;
import com.pqd.application.domain.jenkins.JenkinsJob;
import com.pqd.application.domain.release.ReleaseInfoJenkins;
import com.pqd.application.usecase.jenkins.JenkinsGateway;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JenkinsRestClient implements JenkinsGateway {

    private final RestTemplate restTemplate;

    private <T> ResponseEntity<T> makeHttpRequest(JenkinsInfo jenkinsInfo,
                                                  HttpEntity<String> entity,
                                                  String uri,
                                                  Class<T> responseType) {
        ResponseEntity<T> response;
        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, responseType);

            System.out.println("JUM");
        } catch (ResourceAccessException e) {
            throw new JenkinsConnectionRefusedException(String.format("Connection refused for baseurl %s",
                    jenkinsInfo.getBaseUrl()));
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                throw new JenkinsRestClientException(
                        "Connection unauthorized, probably invalid Jenkins API token or user email");
            }
            throw new JenkinsRestClientException(String.format("%s", exception.getMessage()));
        }
        return response;
    }

        private HttpEntity<String> getAuthorizationHttpEntity(JenkinsInfo jenkinsInfo) {
        String basicAuth = jenkinsInfo.getUsername() + ":" + jenkinsInfo.getToken();
        String encodedString = Base64.getEncoder().encodeToString(basicAuth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encodedString);
        return new HttpEntity<>(headers);
    }

    private ResponseEntity<JenkinsJobResponse> requestJobList(JenkinsInfo jenkinsInfo) {
        HttpEntity<String> entity = getAuthorizationHttpEntity(jenkinsInfo);
        String uri = String.format("%s/api/json?tree=builds", jenkinsInfo.getBaseUrl());
        return makeHttpRequest(jenkinsInfo, entity, uri, JenkinsJobResponse.class);
    }

    private ResponseEntity<JenkinsJobResponse> getBuilds(JenkinsInfo jenkinsInfo) {
        HttpEntity<String> entity = getAuthorizationHttpEntity(jenkinsInfo);
        String uri = String.format("%s/api/json?tree=builds", jenkinsInfo.getBaseUrl());
        return makeHttpRequest(jenkinsInfo, entity, uri, JenkinsJobResponse.class);
    }

    public List<JenkinsJob> getJobsList(JenkinsInfo jenkinsInfo) {
        ResponseEntity<JenkinsJobResponse> response = requestJobList(jenkinsInfo);

        return Arrays.stream(Objects.requireNonNull(response.getBody()).getJobs())
                .map(res -> JenkinsJob.builder()
                        .name(res.getName())
                        .color(res.getColor())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<JenkinsBuild> getJenkinsReleaseInfo(JenkinsInfo jenkinsInfo) {
        HttpEntity<String> entity = getAuthorizationHttpEntity(jenkinsInfo);
        String uri = String.format("%s/api/json?tree=healthReport[score,description],description,name,color,lastBuild[number,result,duration,timestamp],lastSuccessfulBuild[timestamp],lastFailedBuild[number,timestamp],builds[result,building,timestamp,number,duration]", jenkinsInfo.getBaseUrl());

        if(jenkinsInfo.getLastBuildNumber() != null){
            String lastBuildUri = String.format("%s/api/json?tree=lastBuild[number]", jenkinsInfo.getBaseUrl());
            ResponseEntity<JenkinsBuildInfoResponse> lastBuildResponse = makeHttpRequest(jenkinsInfo, entity, lastBuildUri, JenkinsBuildInfoResponse.class);
            JenkinsBuildInfoResponse lastBuildResponseInfo = lastBuildResponse.getBody();
            int lastBuildNumber = lastBuildResponseInfo.getLastBuild().getNumber();
            int lastBuilds = lastBuildNumber - jenkinsInfo.getLastBuildNumber();
            String uriString = String.format("%s/api/json?tree=healthReport[score,description],description,name,color,lastBuild[number,result,duration,timestamp],lastSuccessfulBuild[timestamp],lastFailedBuild[number,timestamp],builds[result,building,timestamp,number,duration]{0,%d}", jenkinsInfo.getBaseUrl(), lastBuilds);
            uri =  URLEncoder.encode(uriString, StandardCharsets.UTF_8);
        }
        ResponseEntity<JenkinsBuildInfoResponse> response = makeHttpRequest(jenkinsInfo, entity, uri, JenkinsBuildInfoResponse.class);

        JenkinsBuildInfoResponse info = response.getBody();

        JenkinsBuild build = JenkinsBuild.builder()
                .lastBuild(info.getLastBuild().getTimestamp())
                .lastBuildNumber(info.getLastBuild().getNumber()).name(info.getName()).status(info.getColor()).description(info.getDescription()).buildReport(info.getHealthReport()[0].getDescription()).buildScore(info.getHealthReport()[0].getScore()).changeFailureRate(info.getChangeFailureRate()).leadTimeForChange(info.getLeadTimeForChange()).deploymentFrequency(info.getDeploymentFrequency()).timeToRestoreService(info.getTimeToRestoreService()).build();
        return Collections.singletonList(build);
    }

    @Override
    public ConnectionResult testJenkinsConnection(JenkinsInfo jenkinsInfo) {
        ConnectionResult connectionResult = ConnectionResult.builder()
                .connectionOk(true)
                .message("Connection successful")
                .build();
        try {
            getBuilds(jenkinsInfo);
        } catch (JenkinsConnectionRefusedException e) {
            connectionResult.setConnectionOk(false);
            connectionResult.setMessage("Could not connect to Jenkins server: " + e.getMessage());
        } catch (JenkinsRestClientException e) {
            connectionResult.setConnectionOk(false);
            connectionResult.setMessage("Connection established, but something went wrong: " + e.getMessage());
        } catch (Exception e) {
            connectionResult.setConnectionOk(false);
            connectionResult.setMessage(e.getMessage());
        }
        return connectionResult;
    }

    @NoArgsConstructor
    public static class JenkinsRestClientException extends RuntimeException {
        public JenkinsRestClientException(String message) {
            super(message);
        }
    }

    @NoArgsConstructor
    public static class JenkinsConnectionRefusedException extends JenkinsRestClientException {
        public JenkinsConnectionRefusedException(String message) {
            super(message);
        }
    }

}
