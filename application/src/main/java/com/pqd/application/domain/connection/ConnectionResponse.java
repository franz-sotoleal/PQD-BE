package com.pqd.application.domain.connection;

import com.pqd.application.usecase.AbstractResponse;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value(staticConstructor = "of")
@EqualsAndHashCode(callSuper = false)
public class ConnectionResponse extends AbstractResponse{

    ConnectionResult connectionResult;
}
