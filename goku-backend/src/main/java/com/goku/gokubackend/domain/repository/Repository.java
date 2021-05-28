package com.goku.gokubackend.domain.repository;

import com.goku.gokubackend.domain.Entity;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public interface Repository<T extends Entity> {
    T create(T newInstance);
    T findById(String id);
    T update(T t);

    default Optional<T> tryToFind(T entity) {
        if (entity.getId().isEmpty()) {
            return entity.hasBusinessKey() ? findByKey(entity) : Optional.empty();
        }
        try {
            return Optional.of(findById(entity.getId().get()));
        } catch (RuntimeException e) {
            return entity.hasBusinessKey() ? findByKey(entity) : empty();
        }
    }

    default Optional<T> findByKey(T entity) {
        return entity.getId().isPresent() ? of(findById(entity.getId().get())) : Optional.empty();
    }

    default T findOrCreate(T entity) {
        return tryToFind(entity).orElse(create(entity));
    }

    default String newId() {
        return UUID.randomUUID().toString();
    }
}
