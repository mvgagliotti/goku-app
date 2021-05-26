package com.goku.gokubackend.domain.repository;

import java.util.UUID;

public interface IRepository {

    default String newId() {
        return UUID.randomUUID().toString();
    }
}
