package com.goku.gokubackend.domain.repository;

import com.goku.gokubackend.domain.User;

import java.util.UUID;

public interface IRepository {

    default String newId() {
        return UUID.randomUUID().toString();
    }

    void update(User user);
}
