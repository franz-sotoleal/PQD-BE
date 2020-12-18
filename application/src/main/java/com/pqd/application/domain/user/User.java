package com.pqd.application.domain.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {

    private final UserId userId;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String email;
    private final String password;
}
