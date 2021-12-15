package com.pqd.adapters.web.product.json.info.jenkins;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JenkinsInfoResultJson {

    String baseUrl;

    String token;

    String username;
}
