package com.goku.gokubackend.domain;

import java.util.Objects;
import java.util.Optional;

public class Street implements Entity {
    private Optional<String> id;
    private final String postalCode;
    private final String streetName;
    private final String district;
    private final City city;

    public Street(Optional<String> id, String postalCode, String streetName, String district, City city) {
        this.id = id;
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.district = district;
        this.city = city;
    }

    @Override
    public Optional<String> getId() {
        return id;
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
        Street that = (Street) o;
        return Objects.equals(postalCode, that.postalCode) &&
                Objects.equals(streetName, that.streetName) &&
                Objects.equals(district, that.district) &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postalCode, streetName, district, city);
    }
}
