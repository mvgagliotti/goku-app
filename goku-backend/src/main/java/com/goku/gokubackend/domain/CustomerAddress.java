package com.goku.gokubackend.domain;

import com.goku.gokubackend.domain.exception.PostalCodeException;
import com.goku.gokubackend.domain.utils.ValidationUtils;

import java.util.Objects;

public class CustomerAddress {

    private final Street street;
    private final AddressInfo addressInfo;

    public CustomerAddress(Street street, AddressInfo addressInfo) {
        ValidationUtils.notNull(street, () -> new PostalCodeException("Postal code cannot be null"));
        ValidationUtils.notNull(addressInfo, () -> new PostalCodeException("Street info cannot be null"));
        this.street = street;
        this.addressInfo = addressInfo;
    }

    public Street getStreet() {
        return street;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerAddress address = (CustomerAddress) o;
        return Objects.equals(street, address.street)
                && Objects.equals(addressInfo, address.addressInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, addressInfo);
    }

    @Override
    public String toString() {
        return "Address{" +
                "postalCode=" + street +
                ", streetInfo=" + addressInfo +
                '}';
    }
}
