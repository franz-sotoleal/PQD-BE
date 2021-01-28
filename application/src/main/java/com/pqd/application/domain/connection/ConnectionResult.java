package com.pqd.application.domain.connection;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ConnectionResult {

    boolean connectionOk;

    String message;
}
