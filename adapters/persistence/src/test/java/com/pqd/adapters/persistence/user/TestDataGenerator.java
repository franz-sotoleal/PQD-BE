package com.pqd.adapters.persistence.user;

import com.pqd.application.domain.user.User;

public class TestDataGenerator {

    public static UserEntity generateUserEntity() {
        return UserEntity.builder()
                         .username("user1")
                         .email("john.doe1@mail.com")
                         .firstName("john45")
                         .lastName("doe22")
                         .password("password5432")
                         .build();
    }

    public static User generateUser() {
        return User.builder()
                         .username("user1")
                         .email("john.doe1@mail.com")
                         .firstName("john45")
                         .lastName("doe22")
                         .password("password5432")
                         .build();
    }
}
