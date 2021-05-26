package com.goku.gokubackend.domain.repository;

import com.goku.gokubackend.domain.User;

public interface UserRepository extends IRepository {
    User create(User user);
    User fetchById(String id);
}
