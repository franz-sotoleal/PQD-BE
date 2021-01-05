package com.pqd.adapters.web.authentication;

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
}
