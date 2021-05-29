package com.goku.gokubackend.application.controller.dto;

public class AddressDTO {
    private final String name;
    private final String postalCode;
    private final CityDTO city;

    public AddressDTO(String name, String postalCode, CityDTO city) {
        this.name = name;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public CityDTO getCity() {
        return city;
    }
}
