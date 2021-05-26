package com.goku.gokubackend.domain.utils;

import org.apache.logging.log4j.util.Supplier;

public class ValidationUtils {

    public static void notNull(Object field, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (field == null) {
            throw exceptionSupplier.get();
        }
    }
}
