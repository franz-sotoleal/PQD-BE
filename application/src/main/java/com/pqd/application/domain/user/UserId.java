package com.pqd.application.domain.user;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value(staticConstructor = "of")
@EqualsAndHashCode
public class UserId {

    public Long id;
}
