package com.goku.gokubackend.application.controller;

import com.goku.gokubackend.application.controller.dto.CityDTO;
import com.goku.gokubackend.application.controller.dto.StreetDTO;
import com.goku.gokubackend.domain.Address;
import com.goku.gokubackend.domain.City;
import com.goku.gokubackend.domain.State;
import com.goku.gokubackend.domain.exception.DomainException;
import com.goku.gokubackend.domain.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AddressController {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @RequestMapping("/address/{postalCode}")
    @ResponseBody
    public StreetDTO get(@PathVariable String postalCode) {
        Address address = addressRepository.findByPostalCode(postalCode).orElseThrow(() -> new DomainException("Address not found"));
        return mapToDTO(address);
    }

    @PostMapping(value = "/address", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public StreetDTO create(@RequestBody StreetDTO streetDTO) {
        Address newInstance = addressRepository.create(mapToAddress(streetDTO));
        return mapToDTO(newInstance);
    }

    private Address mapToAddress(StreetDTO dto) {
        City city = new City(Optional.of(dto.getCity().getId()),
            dto.getCity().getName(),
            new State(dto.getCity().getStateName(), dto.getCity().getStateAbbreviation()));
        Address address = new Address(dto.getPostalCode(), dto.getName(), "", city); //TODO district
        return address;
    }

    private StreetDTO mapToDTO(Address address) {
        return new StreetDTO(address.getPostalCode(), address.getStreetName(),
                new CityDTO(address.getCity().getId().get(),
                        address.getCity().getName(),
                        address.getCity().getState().getName(),
                        address.getCity().getState().getAbbreviation()
                )
        );
    }

}
