package com.pqd.application.usecase.user;

import com.pqd.application.domain.user.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class RegisterUser {

    private final UserGateway userGateway;

    public void execute(Request request) {
        User user = User.builder()
                         .username(request.getUsername())
                         .firstName(request.getFirstName())
                         .lastName(request.getLastName())
                         .email(request.getEmail())
                         .password(request.getPassword())
                         .build();

        // TODO check if username or email doesnt exist already and throw corresponding exception
        // TODO check if username, email or password are not empty

        try {
            userGateway.save(user);
        } catch (Exception e) {
            throw new UserSavingException();
        }
    }

    @Value
    @Builder
    public static class Request {
        String username;
        String password;
        String firstName;
        String lastName;
        String email;
    }

    public static class UserSavingException extends RuntimeException {

    }
}
