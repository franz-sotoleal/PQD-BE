package com.pqd.application.usecase.jenkins;

import com.pqd.application.domain.connection.ConnectionResult;
import com.pqd.application.domain.jenkins.JenkinsBuild;
import com.pqd.application.domain.jenkins.JenkinsInfo;
import com.pqd.application.domain.jenkins.JenkinsJob;
import com.pqd.application.domain.release.ReleaseInfoJenkins;

import java.util.List;

public interface JenkinsGateway {

    List<JenkinsBuild> getJenkinsReleaseInfo(JenkinsInfo jenkinsInfo);

    ConnectionResult testJenkinsConnection(JenkinsInfo jenkinsInfo);
}
