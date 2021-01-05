package com.pqd.adapters.web;

import com.pqd.adapters.web.authentication.RegisterUserRequestJson;
import com.pqd.adapters.web.security.jwt.JwtRequest;
import com.pqd.application.domain.user.User;
import com.pqd.application.domain.user.UserId;

public class TestDataGenerator {

    public static JwtRequest generateJwtRequest() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("user");
        jwtRequest.setPassword("password");
        return jwtRequest;
    }

    public static RegisterUserRequestJson generateRegisterUserInput() {
        return RegisterUserRequestJson.builder()
                                      .username("user1")
                                      .email("john.doe1@mail.com")
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("password5432")
                                      .build();
    }

    public static RegisterUserRequestJson generateRegisterUserInputWithShortPassword() {
        return RegisterUserRequestJson.builder()
                                      .username("user1")
                                      .email("john.doe1@mail.com")
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("pas")
                                      .build();
    }

    public static User generateUser() {
        return User.builder()
                   .userId(UserId.of(12345L))
                   .username("user1")
                   .email("john.doe1@mail.com")
                   .firstName("john45")
                   .lastName("doe22")
                   .password("pas")
                   .build();
    }
}
