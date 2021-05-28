package com.goku.gokubackend.domain.exception;

public class AddressUpdateException extends DomainException {

    public AddressUpdateException(String address) {
        super("Address not found" + address);
    }
}
