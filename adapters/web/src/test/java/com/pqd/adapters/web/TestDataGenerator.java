package com.pqd.adapters.web;

import com.pqd.adapters.web.authentication.RegisterUserInput;
import com.pqd.adapters.web.security.jwt.JwtRequest;

public class TestDataGenerator {

    public static JwtRequest generateJwtRequest() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("user");
        jwtRequest.setPassword("password");
        return jwtRequest;
    }

    public static RegisterUserInput generateRegisterUserInput() {
        return RegisterUserInput.builder()
                                .username("user1")
                                .email("john.doe1@mail.com")
                                .firstName("john45")
                                .lastName("doe22")
                                .password("password5432")
                                .build();
    }

    public static RegisterUserInput generateRegisterUserInputWithShortPassword() {
        return RegisterUserInput.builder()
                                .username("user1")
                                .email("john.doe1@mail.com")
                                .firstName("john45")
                                .lastName("doe22")
                                .password("pas")
                                .build();
    }
}
