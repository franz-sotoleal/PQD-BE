package com.pqd.adapters.persistence.user;

import com.pqd.application.domain.user.User;
import com.pqd.application.domain.user.UserId;
import com.pqd.application.usecase.user.UserGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;

@Component //TODO make persistence adapter component
@Transactional
@AllArgsConstructor
public class UserAdapter implements UserGateway {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username).map(toUser());
    }

    private Function<UserEntity, User> toUser() {
        return user -> User.builder()
                           .userId(UserId.of(user.getId()))
                           .username(user.getUsername())
                           .firstName(user.getFirstName())
                           .lastName(user.getLastName())
                           .email(user.getEmail())
                           .password(user.getPassword())
                           .build();
    }
}
