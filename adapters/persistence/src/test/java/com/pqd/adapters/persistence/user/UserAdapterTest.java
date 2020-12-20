package com.pqd.adapters.persistence.user;

import com.pqd.application.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserAdapterTest {

    private UserRepository userRepository;
    private UserAdapter userAdapter;

    @Captor
    private ArgumentCaptor<UserEntity> captor;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userAdapter = new UserAdapter(userRepository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GIVEN_user_entity_exists_WHEN_user_searched_by_username_THEN_user_returned() {
        UserEntity userEntity = TestDataGenerator.generateUserEntity();
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(userEntity));

        Optional<User> result = userAdapter.findByUsername("user1");

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getUsername()).isEqualTo(userEntity.getUsername());
        assertThat(result.get().getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(result.get().getPassword()).isEqualTo(userEntity.getPassword());
        assertThat(result.get().getFirstName()).isEqualTo(userEntity.getFirstName());
        assertThat(result.get().getLastName()).isEqualTo(userEntity.getLastName());
    }

    @Test
    void GIVEN_user_entity_does_not_exist_WHEN_user_searched_by_username_THEN_nothing_returned() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        Optional<User> result = userAdapter.findByUsername("user1");

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void GIVEN_user_entity_exists_WHEN_user_searched_by_email_THEN_user_returned() {
        UserEntity userEntity = TestDataGenerator.generateUserEntity();
        when(userRepository.findByEmail("john.doe1@mail.com")).thenReturn(Optional.of(userEntity));

        Optional<User> result = userAdapter.findByEmail("john.doe1@mail.com");

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getUsername()).isEqualTo(userEntity.getUsername());
        assertThat(result.get().getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(result.get().getPassword()).isEqualTo(userEntity.getPassword());
        assertThat(result.get().getFirstName()).isEqualTo(userEntity.getFirstName());
        assertThat(result.get().getLastName()).isEqualTo(userEntity.getLastName());
    }

    @Test
    void GIVEN_user_entity_does_not_exist_WHEN_user_searched_by_email_THEN_nothing_returned() {
        when(userRepository.findByEmail("john.doe1@mail.com")).thenReturn(Optional.empty());

        Optional<User> result = userAdapter.findByEmail("john.doe1@mail.com");

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void GIVEN_user_WHEN_saving_user_entity_THEN_user_entity_passed_and_saved() {
        User user = TestDataGenerator.generateUser();

        userAdapter.save(user);

        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue()).hasSameClassAs(TestDataGenerator.generateUserEntity());
    }
}
