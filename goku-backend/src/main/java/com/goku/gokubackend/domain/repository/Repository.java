package com.goku.gokubackend.domain.repository;

import com.goku.gokubackend.domain.Entity;

import java.util.Optional;
import java.util.UUID;

public interface Repository<T extends Entity> {
    T create(T newInstance);
    T findById(String id);
    T update(T t);

    default Optional<T> tryToFind(String id) {
        try {
            return Optional.of(findById(id));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    default T findOrCreate(T entity) {
        if (entity.getId().isEmpty()) {
            return create(entity);
        }
        return tryToFind(entity.getId().get()).orElse(create(entity));
    }

    default String newId() {
        return UUID.randomUUID().toString();
    }
}
