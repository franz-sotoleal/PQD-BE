package com.pqd.adapters.web.product.json.info.jira;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pqd.application.domain.jira.JiraInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JiraInfoRequestJson {

    @JsonProperty("baseUrl")
    String baseUrl;

    @JsonProperty("boardId")
    Long boardId;

    @JsonProperty("userEmail")
    String userEmail;

    @JsonProperty("token")
    String token;

    public JiraInfo toJiraInfo() {
        return JiraInfo.builder()
                       .baseUrl(baseUrl)
                       .boardId(boardId)
                       .userEmail(userEmail)
                       .token(token)
                       .build();
    }
}
