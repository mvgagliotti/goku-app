package com.goku.gokubackend.domain.exception;

public class DuplicatedAddressException extends RuntimeException {

    public DuplicatedAddressException(String message) {
        super(message);
    }
}
