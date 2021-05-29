package com.goku.gokubackend.application.controller.dto;

public class StreetDTO {
    private final String name;
    private final String postalCode;
    private final CityDTO city;
    private final int number;
    private final String description;

    public StreetDTO(String postalCode, String name, CityDTO city, int number, String description) {
        this.name = name;
        this.postalCode = postalCode;
        this.city = city;
        this.number = number;
        this.description = description;
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

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }
}
