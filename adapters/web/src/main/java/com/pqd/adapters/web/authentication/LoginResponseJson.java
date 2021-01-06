package com.pqd.adapters.web.authentication;

import com.pqd.application.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponseJson {

    Long userId;

    String username;

    String firstName;

    String lastName;

    String email;

    String jwt;

    public static LoginResponseJson buildResopnseJson(User user, String token) {
        return LoginResponseJson.builder()
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .userId(user.getUserId().getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .jwt(token)
                                .build();
    }
}
