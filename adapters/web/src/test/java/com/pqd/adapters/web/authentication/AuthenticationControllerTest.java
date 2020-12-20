package com.pqd.adapters.web.authentication;

import com.pqd.adapters.web.TestDataGenerator;
import com.pqd.adapters.web.security.jwt.JwtRequest;
import com.pqd.adapters.web.security.jwt.JwtResponse;
import com.pqd.adapters.web.security.jwt.JwtTokenUtil;
import com.pqd.adapters.web.security.jwt.JwtUserDetailsService;
import com.pqd.application.usecase.user.RegisterUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationControllerTest {

    private AuthenticationController controller;
    private RegisterUser registerUser;

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private PasswordEncoder bcryptEncoder;

    @Captor
    private ArgumentCaptor<RegisterUser.Request> captor;

    @BeforeEach
    void setup() {
        JwtUserDetailsService jwtInMemoryUserDetailsService = mock(JwtUserDetailsService.class);
        registerUser = mock(RegisterUser.class);
        authenticationManager = mock(AuthenticationManager.class);
        jwtTokenUtil = mock(JwtTokenUtil.class);
        bcryptEncoder = mock(PasswordEncoder.class);
        controller = new AuthenticationController(authenticationManager,
                                                  jwtTokenUtil,
                                                  jwtInMemoryUserDetailsService,
                                                  registerUser,
                                                  bcryptEncoder);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_valid_credentials_WHEN_login_THEN_jwt_token_returned() {
        JwtRequest jwtRequest = TestDataGenerator.generateJwtRequest();
        when(jwtTokenUtil.generateToken(any())).thenReturn("token");

        ResponseEntity<JwtResponse> response = controller.loginAndGenerateAuthenticationToken(jwtRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getJwttoken()).isEqualTo("token");
    }

    @Test
    void GIVEN_invalid_credentials_WHEN_login_THEN_exception_thrown() {
        JwtRequest jwtRequest = TestDataGenerator.generateJwtRequest();
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException(""));

        Exception exception =
                assertThrows(Exception.class, () -> controller.loginAndGenerateAuthenticationToken(jwtRequest));
        assertThat(exception).hasStackTraceContaining("BadCredentialsException");
    }

    @Test
    void GIVEN_valid_data_WHEN_registering_THEN_password_encrypted_and_user_saved() {
        RegisterUserRequestJson registerUserRequestJson = TestDataGenerator.generateRegisterUserInput();
        when(bcryptEncoder.encode(any())).thenReturn("EncryptedPassword");
        ResponseEntity<Void> response = controller.register(registerUserRequestJson);

        verify(registerUser).execute(captor.capture());
        assertThat(captor.getValue().getPassword()).isEqualTo("EncryptedPassword");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void GIVEN_short_password_WHEN_registering_THEN_exception_thrown() {
        RegisterUserRequestJson registerUserRequestJson = TestDataGenerator.generateRegisterUserInputWithShortPassword();

        Exception exception =
                assertThrows(Exception.class, () -> controller.register(registerUserRequestJson));
        assertThat(exception).hasStackTraceContaining("InvalidFieldException");
        assertThat(exception).hasStackTraceContaining("Password too short");
    }


}
