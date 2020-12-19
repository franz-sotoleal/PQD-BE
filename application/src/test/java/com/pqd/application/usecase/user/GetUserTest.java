package com.pqd.application.usecase.user;

import com.pqd.application.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetUserTest {

    private UserGateway userGateway;
    private GetUser getUser;

    @BeforeEach
    void setup() {
        userGateway = mock(UserGateway.class);
        getUser = new GetUser(userGateway);
    }

    @Test
    void GIVEN_user_exists_WHEN_user_requested_THEN_user_returned() {
        User existingUser = UserTestDataGenerator.generateUser();

        when(userGateway.findByUsername("user1")).thenReturn(Optional.of(existingUser));
        User actual = getUser.execute(GetUser.Request.of("user1")).getUser();

        verify(userGateway).findByUsername("user1");
        assertThat(actual.getUsername()).isEqualTo(existingUser.getUsername());
        assertThat(actual.getEmail()).isEqualTo(existingUser.getEmail());
        assertThat(actual.getFirstName()).isEqualTo(existingUser.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(existingUser.getLastName());
        assertThat(actual.getPassword()).isEqualTo(existingUser.getPassword());
    }

    @Test
    void GIVEN_user_does_not_exist_WHEN_user_requested_THEN_exception_thrown() {
        when(userGateway.findByUsername("user1")).thenReturn(Optional.empty());

        GetUser.UserNotFoundException exception =
                assertThrows(GetUser.UserNotFoundException.class, () -> getUser.execute(GetUser.Request.of("user1")));

        verify(userGateway).findByUsername("user1");
        assertThat(exception).hasStackTraceContaining("UserNotFound");
    }

}
