package com.goku.gokubackend.infrastructure;

import org.apache.logging.log4j.util.Supplier;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

public interface SpringEmptyResultHandler {

    default <T> T handleEmptyThrow(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (EmptyResultDataAccessException ex) {
            //TODO: review this solution, this message is not clear
            throw new RuntimeException("Entity not found");
        }
    }

    default <T> Optional<T> handleEmpty(Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

}
