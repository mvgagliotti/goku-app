package com.goku.gokubackend.domain;

import com.goku.gokubackend.domain.utils.ValidationUtils;

import java.util.Objects;

public class User {
    private final String id;
    private final String username;
    private final String password;
    private final Roles roles;

    public User(String id, String username, String password, Roles roles) {
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

    public Roles getRoles() {
        return roles;
    }

    public User withId(String id) {
        ValidationUtils.notNull(id, () -> new RuntimeException("Id cannot be null"));
        return new User(id, username, password, roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
