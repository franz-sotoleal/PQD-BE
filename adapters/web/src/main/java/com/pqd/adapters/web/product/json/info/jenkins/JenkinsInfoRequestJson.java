package com.pqd.adapters.web.product.json.info.jenkins;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pqd.application.domain.jenkins.JenkinsInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JenkinsInfoRequestJson {

    @JsonProperty("baseUrl")
    String baseUrl;


    @JsonProperty("username")
    String username;

    @JsonProperty("token")
    String token;

    public JenkinsInfo toJenkinsInfo() {
        return JenkinsInfo.builder()
                .baseUrl(baseUrl)
                .username(username)
                .token(token)
                .build();
    }
}

