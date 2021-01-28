package com.pqd.adapters.web.product.json;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ConnectionResultJson {

    boolean connectionOk;

    String message;
}
