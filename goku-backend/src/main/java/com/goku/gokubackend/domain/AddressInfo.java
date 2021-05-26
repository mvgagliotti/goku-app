package com.goku.gokubackend.domain;

import com.goku.gokubackend.domain.exception.PostalCodeException;
import com.goku.gokubackend.domain.utils.ValidationUtils;

import java.util.Objects;

public class AddressInfo {
    private final int number;
    private final String description;

    public AddressInfo(int number, String description) {
        ValidationUtils.notNull(number, () -> new PostalCodeException("Address number cannot be null"));
        ValidationUtils.notNull(description, () -> new PostalCodeException("Address description cannot be null"));
        this.description = description;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressInfo that = (AddressInfo) o;
        return number == that.number && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, number);
    }
}
