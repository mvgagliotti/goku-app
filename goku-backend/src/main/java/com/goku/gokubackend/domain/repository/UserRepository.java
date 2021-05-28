package com.goku.gokubackend.domain.repository;

import com.goku.gokubackend.domain.User;

public interface UserRepository extends Repository<User> {
    User create(User newInstance);
    User fetchById(String id);
    User fetchByUsername(String username);
}
