package com.goku.gokubackend.application.controller.dto;

public class StreetDTO {
    private final String name;
    private final String postalCode;
    private final CityDTO city;

    public StreetDTO(String postalCode, String name, CityDTO city) {
        this.name = name;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getName() {
        return name;
    }

    public CityDTO getCity() {
        return city;
    }
}
