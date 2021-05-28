package com.goku.gokubackend.domain;

import com.goku.gokubackend.domain.exception.PostalCodeException;
import com.goku.gokubackend.domain.utils.ValidationUtils;

import java.util.Objects;

public class CustomerAddress {

    private final Address address;
    private final AddressInfo addressInfo;

    public CustomerAddress(Address address, AddressInfo addressInfo) {
        ValidationUtils.notNull(address, () -> new PostalCodeException("Postal code cannot be null"));
        ValidationUtils.notNull(addressInfo, () -> new PostalCodeException("Street info cannot be null"));
        this.address = address;
        this.addressInfo = addressInfo;
    }

    public Address getStreetAddress() {
        return address;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerAddress address = (CustomerAddress) o;
        return Objects.equals(this.address, address.address)
                && Objects.equals(addressInfo, address.addressInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, addressInfo);
    }

    @Override
    public String toString() {
        return "Address{" +
                "postalCode=" + address +
                ", streetInfo=" + addressInfo +
                '}';
    }
}
