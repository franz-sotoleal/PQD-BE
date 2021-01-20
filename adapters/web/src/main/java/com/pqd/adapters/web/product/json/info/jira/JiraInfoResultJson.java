package com.pqd.adapters.web.product.json.info.jira;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JiraInfoResultJson {

    String baseUrl;

    Long boardId;

    String token;

    String userEmail;
}
