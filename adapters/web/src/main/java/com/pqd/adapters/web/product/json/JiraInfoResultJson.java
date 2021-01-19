package com.pqd.adapters.web.product.json;

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
