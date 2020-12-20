package com.pqd.application.usecase.user;

import com.pqd.application.domain.user.User;

public class UserTestDataGenerator {

    public static User generateUser() {
        return User.builder()
                   .username("user1")
                   .email("john.doe1@mail.com")
                   .firstName("john45")
                   .lastName("doe22")
                   .password("password5432")
                   .build();
    }

    public static RegisterUser.Request generateRegisterUserRequest() {
        return RegisterUser.Request.builder()
                   .username("user1")
                   .email("john.doe1@mail.com")
                   .firstName("john45")
                   .lastName("doe22")
                   .password("password5432")
                   .build();
    }

    public static RegisterUser.Request generateRegisterUserRequestWithInvalidEmail() {
        return RegisterUser.Request.builder()
                                   .username("user1")
                                   .email("john.mail.com")
                                   .firstName("john45")
                                   .lastName("doe22")
                                   .password("password5432")
                                   .build();
    }

    public static RegisterUser.Request generateRegisterUserRequestWithInvalidUsername() {
        return RegisterUser.Request.builder()
                                   .username("use")
                                   .email("john.doe1@mail.com")
                                   .firstName("john45")
                                   .lastName("doe22")
                                   .password("password5432")
                                   .build();
    }
}
