package com.goku.gokubackend.infrastructure.repository;

import org.apache.logging.log4j.util.Supplier;
import org.springframework.dao.EmptyResultDataAccessException;

public interface SpringEmptyResultHandler {

    default <T> T handleEmpty(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (EmptyResultDataAccessException ex) {
            throw new RuntimeException("User not found");
        }
    }

}
