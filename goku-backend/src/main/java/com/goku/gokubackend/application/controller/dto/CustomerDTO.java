package com.goku.gokubackend.application.controller.dto;

import java.util.List;

public class CustomerDTO {
    private final String id;
    private final String name;
    private final List<StreetDTO> addresses;

    public CustomerDTO(String id, String name, List<StreetDTO> addresses) {
        this.id = id;
        this.name = name;
        this.addresses = addresses;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<StreetDTO> getAddresses() {
        return addresses;
    }
}
