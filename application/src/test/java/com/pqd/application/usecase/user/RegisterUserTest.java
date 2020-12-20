package com.pqd.application.usecase.user;

import com.pqd.application.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RegisterUserTest {

    private UserGateway userGateway;
    private RegisterUser registerUser;

    @Captor
    private ArgumentCaptor<User> captor;

    @BeforeEach
    void setup() {
        userGateway = mock(UserGateway.class);
        registerUser = new RegisterUser(userGateway);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_correct_request_WHEN_saving_user_THEN_user_saved() {
        RegisterUser.Request request = UserTestDataGenerator.generateRegisterUserRequest();
        when(userGateway.findByEmail(any())).thenReturn(Optional.empty());
        when(userGateway.findByUsername(any())).thenReturn(Optional.empty());

        registerUser.execute(request);

        verify(userGateway).save(captor.capture());
        assertThat(request.getUsername()).isEqualTo(captor.getValue().getUsername());
        assertThat(request.getEmail()).isEqualTo(captor.getValue().getEmail());
        assertThat(request.getFirstName()).isEqualTo(captor.getValue().getFirstName());
        assertThat(request.getLastName()).isEqualTo(captor.getValue().getLastName());
        assertThat(request.getPassword()).isEqualTo(captor.getValue().getPassword());
    }

    @Test
    void GIVEN_invalid_email_WHEN_saving_user_THEN_exception_thrown() {
        RegisterUser.Request request = UserTestDataGenerator.generateRegisterUserRequestWithInvalidEmail();

        RegisterUser.InvalidFieldException exception =
                assertThrows(RegisterUser.InvalidFieldException.class, () -> registerUser.execute(request));

        assertThat(exception).hasStackTraceContaining("InvalidFieldException");
        assertThat(exception).hasStackTraceContaining("Email invalid");
    }

    @Test
    void GIVEN_invalid_username_WHEN_saving_user_THEN_exception_thrown() {
        RegisterUser.Request request = UserTestDataGenerator.generateRegisterUserRequestWithInvalidUsername();

        RegisterUser.InvalidFieldException exception =
                assertThrows(RegisterUser.InvalidFieldException.class, () -> registerUser.execute(request));

        assertThat(exception).hasStackTraceContaining("InvalidFieldException");
        assertThat(exception).hasStackTraceContaining("Username too short");
    }

    @Test
    void GIVEN_email_exists_WHEN_saving_user_THEN_exception_thrown() {
        when(userGateway.findByEmail(any())).thenReturn(Optional.of(UserTestDataGenerator.generateUser()));
        RegisterUser.Request request = UserTestDataGenerator.generateRegisterUserRequest();

        RegisterUser.InvalidFieldException exception =
                assertThrows(RegisterUser.InvalidFieldException.class, () -> registerUser.execute(request));

        assertThat(exception).hasStackTraceContaining("InvalidFieldException");
        assertThat(exception).hasStackTraceContaining("Email already in use");
    }

    @Test
    void GIVEN_username_exists_WHEN_saving_user_THEN_exception_thrown() {
        when(userGateway.findByUsername(any())).thenReturn(Optional.of(UserTestDataGenerator.generateUser()));
        RegisterUser.Request request = UserTestDataGenerator.generateRegisterUserRequest();

        RegisterUser.InvalidFieldException exception =
                assertThrows(RegisterUser.InvalidFieldException.class, () -> registerUser.execute(request));

        assertThat(exception).hasStackTraceContaining("InvalidFieldException");
        assertThat(exception).hasStackTraceContaining("Username already in use");
    }

}
