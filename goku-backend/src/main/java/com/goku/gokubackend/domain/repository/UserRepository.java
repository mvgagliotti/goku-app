package com.goku.gokubackend.domain.repository;

import com.goku.gokubackend.domain.User;

import java.util.Optional;

public interface UserRepository extends Repository<User> {
    User create(User newInstance);
    User fetchById(String id);
    Optional<User> fetchByUsername(String username);
}
