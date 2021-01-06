package com.pqd.integration.web;

import com.pqd.adapters.web.authentication.RegisterUserRequestJson;
import com.pqd.adapters.web.security.jwt.JwtRequest;
import com.pqd.integration.TestContainerBase;
import com.pqd.integration.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class AuthenticationControllerIntegrationTest extends TestContainerBase {

    @Autowired
    MockMvc mvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
    void GIVEN_correct_credentials_WHEN_login_THEN_user_info_with_jwt_returned() throws Exception {
        JwtRequest jwtRequest = TestDataGenerator.generateJwtRequestWithValidCredentials();
        mvc.perform(post("/api/authentication/login")
                            .content(objectMapper.writeValueAsString(jwtRequest))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.username", is("user")))
           .andExpect(jsonPath("$.userId", is(1)))
           .andExpect(jsonPath("$.firstName", is("john")))
           .andExpect(jsonPath("$.lastName", is("doe")))
           .andExpect(jsonPath("$.email", is("john.doe@mail.com")))
           .andExpect(jsonPath("$.jwt", isA(String.class)));
    }

    @Test
    @Transactional
    void GIVEN_invalid_credentials_WHEN_login_THEN_401_returned() throws Exception {
        JwtRequest jwtRequest = TestDataGenerator.generateJwtRequestWithInvalidCredentials();
        mvc.perform(post("/api/authentication/login")
                            .content(objectMapper.writeValueAsString(jwtRequest))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().is4xxClientError())
           .andExpect(content().string("INVALID_CREDENTIALS"));
    }

    @Test
    @Transactional
    void GIVEN_correct_data_WHEN_registering_user_THEN_200_returned() throws Exception {
        RegisterUserRequestJson input = TestDataGenerator.generateRegisterUserRequestJson();
        mvc.perform(post("/api/authentication/register")
                            .content(objectMapper.writeValueAsString(input))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void GIVEN_invalid_username_WHEN_registering_user_THEN_400_returned_with_message() throws Exception {
        RegisterUserRequestJson input = TestDataGenerator.generateRegisterUserRequestJsonWithInvalidUsername();
        mvc.perform(post("/api/authentication/register")
                            .content(objectMapper.writeValueAsString(input))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().is4xxClientError())
           .andExpect(content().string("Username too short"));
    }

    @Test
    @Transactional
    void GIVEN_invalid_password_WHEN_registering_user_THEN_400_returned_with_message() throws Exception {
        RegisterUserRequestJson input = TestDataGenerator.generateRegisterUserRequestJsonWithInvalidPassword();
        mvc.perform(post("/api/authentication/register")
                            .content(objectMapper.writeValueAsString(input))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().is4xxClientError())
           .andExpect(content().string("Password too short"));
    }

    @Test
    @Transactional
    void GIVEN_invalid_email_WHEN_registering_user_THEN_400_returned_with_message() throws Exception {
        RegisterUserRequestJson input = TestDataGenerator.generateRegisterUserRequestJsonWithInvalidEmail();
        mvc.perform(post("/api/authentication/register")
                            .content(objectMapper.writeValueAsString(input))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().is4xxClientError())
           .andExpect(content().string("Email invalid"));
    }

    @Test
    @Transactional
    void GIVEN_existing_email_WHEN_registering_user_THEN_400_returned_with_message() throws Exception {
        RegisterUserRequestJson input = TestDataGenerator.generateRegisterUserRequestJsonWithExistingEmail();
        mvc.perform(post("/api/authentication/register")
                            .content(objectMapper.writeValueAsString(input))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().is4xxClientError())
           .andExpect(content().string("Email already in use"));
    }

    @Test
    @Transactional
    void GIVEN_existing_username_WHEN_registering_user_THEN_400_returned_with_message() throws Exception {
        RegisterUserRequestJson input = TestDataGenerator.generateRegisterUserRequestJsonWithExistingUsername();
        mvc.perform(post("/api/authentication/register")
                            .content(objectMapper.writeValueAsString(input))
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().is4xxClientError())
           .andExpect(content().string("Username already in use"));
    }
}
