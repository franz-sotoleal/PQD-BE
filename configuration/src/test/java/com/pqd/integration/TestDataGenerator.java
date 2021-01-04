package com.pqd.integration;

import com.pqd.adapters.web.authentication.RegisterUserRequestJson;
import com.pqd.adapters.web.security.jwt.JwtRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Base64;

public class TestDataGenerator {
    public static JwtRequest generateJwtRequestWithValidCredentials() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword("password");
        jwtRequest.setUsername("user");
        return jwtRequest;
    }

    public static JwtRequest generateJwtRequestWithInvalidCredentials() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword("invalidpass");
        jwtRequest.setUsername("user");
        return jwtRequest;
    }

    public static RegisterUserRequestJson generateRegisterUserRequestJson() {
        return RegisterUserRequestJson.builder()
                                      .username("user1")
                                      .email("john.doe1@mail.com")
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("password5432")
                                      .build();
    }

    public static RegisterUserRequestJson generateRegisterUserRequestJsonWithInvalidUsername() {
        return RegisterUserRequestJson.builder()
                                      .username("use") // too short
                                      .email("john.doe1@mail.com")
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("password5432")
                                      .build();
    }

    public static RegisterUserRequestJson generateRegisterUserRequestJsonWithInvalidPassword() {
        return RegisterUserRequestJson.builder()
                                      .username("user1")
                                      .email("john.doe1@mail.com")
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("pas") // too short
                                      .build();
    }

    public static RegisterUserRequestJson generateRegisterUserRequestJsonWithInvalidEmail() {
        return RegisterUserRequestJson.builder()
                                      .username("user1")
                                      .email("john.doe1.mail") // not email address
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("password5432")
                                      .build();
    }

    public static RegisterUserRequestJson generateRegisterUserRequestJsonWithExistingEmail() {
        return RegisterUserRequestJson.builder()
                                      .username("user1")
                                      .email("john.doe@mail.com")
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("password5432")
                                      .build();
    }

    public static RegisterUserRequestJson generateRegisterUserRequestJsonWithExistingUsername() {
        return RegisterUserRequestJson.builder()
                                      .username("user")
                                      .email("john.doe1@mail.com")
                                      .firstName("john45")
                                      .lastName("doe22")
                                      .password("password5432")
                                      .build();
    }

    public static HttpHeaders generateValidHttpHeadersForMessagingController() {
        String tokenBase = "8257cc3a6b0610da1357f73e03524b090658553a" + ":";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, basicAuth);
        return headers;
    }

    public static HttpHeaders generateInvalidTokenHttpHeadersForMessagingController() {
        String tokenBase = "invalid" + ":";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, basicAuth);
        return headers;
    }

    public static HttpHeaders generateInvalidHttpHeadersForMessagingController() {
        String tokenBase = "invalid" + ":";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("AuTHORizaTION", basicAuth);
        return headers;
    }
}
