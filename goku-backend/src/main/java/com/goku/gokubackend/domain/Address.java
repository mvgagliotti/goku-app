package com.goku.gokubackend.domain;

import com.goku.gokubackend.domain.utils.ValidationUtils;

import java.util.Objects;
import java.util.Optional;

public class Address implements Entity {
    private final String postalCode;
    private final String streetName;
    private final String district;
    private final City city;

    public Address(String postalCode, String streetName, String district, City city) {
        ValidationUtils.notNull(postalCode, () -> new RuntimeException("Postal Code is mandatory"));
        ValidationUtils.notNull(streetName, () -> new RuntimeException("Street name is mandatory"));
        ValidationUtils.notNull(district, () -> new RuntimeException("District Code is mandatory"));
        ValidationUtils.notNull(city, () -> new RuntimeException("City is mandatory"));
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.district = district;
        this.city = city;
    }

    @Override
    public Optional<String> getId() {
        return Optional.of(postalCode);
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getDistrict() {
        return district;
    }

    public City getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address that = (Address) o;
        return Objects.equals(postalCode, that.postalCode) &&
                Objects.equals(streetName, that.streetName) &&
                Objects.equals(district, that.district) &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postalCode, streetName, district, city);
    }

    @Override
    public String toString() {
        return "Address{" +
                "postalCode='" + postalCode + '\'' +
                ", streetName='" + streetName + '\'' +
                ", district='" + district + '\'' +
                ", city=" + city +
                '}';
    }
}
