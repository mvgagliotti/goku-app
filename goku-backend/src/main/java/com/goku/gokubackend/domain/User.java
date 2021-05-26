package com.goku.gokubackend.domain;

import com.goku.gokubackend.domain.utils.ValidationUtils;

public class User {
    private final String id;
    private final String username;
    private final String password;
    private final String roles;

    public User(String id, String username, String password, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRoles() {
        return roles;
    }

    public User withId(String id) {
        ValidationUtils.notNull(id, () -> new RuntimeException("Id cannot be null"));
        return new User(id, username, password, roles);
    }
}
