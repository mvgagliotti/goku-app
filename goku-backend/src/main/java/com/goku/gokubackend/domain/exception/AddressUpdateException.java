package com.goku.gokubackend.domain.exception;

public class AddressUpdateException extends RuntimeException {

    public AddressUpdateException(String address) {
        super("Address not found" + address.toString());
    }
}
