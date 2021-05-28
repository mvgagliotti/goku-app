package com.goku.gokubackend.application.controller;

import com.goku.gokubackend.application.controller.dto.CityDTO;
import com.goku.gokubackend.application.controller.dto.StreetDTO;
import com.goku.gokubackend.domain.Address;
import com.goku.gokubackend.domain.exception.DomainException;
import com.goku.gokubackend.domain.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressSearchController {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressSearchController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @RequestMapping("/address/{postalCode}")
    @ResponseBody
    public StreetDTO get(@PathVariable String postalCode) {
        Address address = addressRepository.findByPostalCode(postalCode).orElseThrow(() -> new DomainException("Address not found"));
        return mapToDTO(address);
    }

    private StreetDTO mapToDTO(Address address) {
        return new StreetDTO(address.getPostalCode(), address.getStreetName(),
                new CityDTO(address.getCity().getId().get(), address.getCity().getName()));
    }

}
