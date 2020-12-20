package com.pqd.application.usecase.user;

import com.pqd.application.domain.user.User;
import com.pqd.application.usecase.UseCase;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.transaction.Transactional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@UseCase
@Transactional
public class RegisterUser {

    private final UserGateway userGateway;

    public void execute(Request request) {
        validateCompulsoryFields(request.getUsername(), request.getEmail());
        userGateway.findByEmail(request.getEmail()).ifPresent(r -> {throw new InvalidFieldException("Email already in use");});
        userGateway.findByUsername(request.getUsername()).ifPresent(r -> {throw new InvalidFieldException("Username already in use");});

        User user = User.builder()
                        .username(request.getUsername())
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .build();

        userGateway.save(user);
    }

    private void validateCompulsoryFields(String username, String email) {
        if (username.length() < 4) {
            throw new InvalidFieldException("Username too short");
        } else if (!isValidEmail(email)) {
            throw new InvalidFieldException("Email invalid");
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
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

    public static class InvalidFieldException extends RuntimeException {
        public InvalidFieldException(String message) {
            super(message);
        }
    }
}
