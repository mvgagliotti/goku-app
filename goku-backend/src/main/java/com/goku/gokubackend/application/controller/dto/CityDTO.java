package com.goku.gokubackend.application.controller.dto;

public class CityDTO {
    private final String id;
    private final String name;
    private final String stateName;
    private final String stateAbbreviation;

    public CityDTO(String id, String name, String stateName, String stateAbbreviation) {
        this.id = id;
        this.name = name;
        this.stateName = stateName;
        this.stateAbbreviation = stateAbbreviation;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStateName() {
        return stateName;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }
}
