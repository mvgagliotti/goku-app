package com.goku.gokubackend.application.controller.dto;

public class CityDTO {
    private final String id;
    private final String name;

    public CityDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
