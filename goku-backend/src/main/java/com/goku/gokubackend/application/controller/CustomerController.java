package com.goku.gokubackend.application.controller;

import com.goku.gokubackend.application.controller.dto.CityDTO;
import com.goku.gokubackend.application.controller.dto.CustomerDTO;
import com.goku.gokubackend.application.controller.dto.StreetDTO;
import com.goku.gokubackend.domain.*;
import com.goku.gokubackend.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping("/customer/{id}")
    @ResponseBody
    public CustomerDTO get(@PathVariable String id) {
        Customer customer = customerRepository.findById(id);
        return mapToDTO(customer);
    }

    @PutMapping(value = "/customer/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CustomerDTO update(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        Customer customer = mapToCustomer(id, customerDTO);
        customer = customerRepository.update(customer);
        return mapToDTO(customer);
    }

    private Customer mapToCustomer(String id, CustomerDTO customerDTO) {
        Addresses addresses = new Addresses(customerDTO
                .getAddresses()
                .stream()
                .map(x -> {
                    City city = new City(
                            Optional.of(x.getCity().getId()),
                            x.getCity().getName(),
                            new State("", ""));
                    Address address = new Address(x.getPostalCode(), x.getName(), "", city);
                    CustomerAddress customerAddress = new CustomerAddress(address, new AddressInfo(204, ""));
                    return customerAddress;
                }).collect(Collectors.toList()));

        return new Customer(Optional.of(id), customerDTO.getName(), addresses);
    }

    private CustomerDTO mapToDTO(Customer customer) {
        return new CustomerDTO(
                customer.getId().get(),
                customer.getName(),
                customer
                        .getDeliveryAddresses()
                        .get()
                        .stream()
                        .map(x -> new StreetDTO(
                                x.getStreetAddress().getPostalCode(),
                                x.getStreetAddress().getStreetName(),
                                new CityDTO(x.getStreetAddress().getCity().getId().get(),
                                        x.getStreetAddress().getCity().getName(),
                                        x.getStreetAddress().getCity().getState().getName(),
                                        x.getStreetAddress().getCity().getState().getAbbreviation()
                                )))
                        .collect(Collectors.toList())
        );
    }

}
