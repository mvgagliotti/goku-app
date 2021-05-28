package com.goku.gokubackend.fixtures;

import com.goku.gokubackend.domain.Roles;
import com.goku.gokubackend.domain.User;

import java.util.Optional;

public class UserFixture {

    public static User aUser() {
        return new User(Optional.of("1"), "user@email.com",
                "$2a$10$qb2lZtf03MS4MxGFoSeQreFFPwbwsK.4oBT5ebCVGwFIKJH6N2qae", new Roles("ROLE_USER"));
    }
}
