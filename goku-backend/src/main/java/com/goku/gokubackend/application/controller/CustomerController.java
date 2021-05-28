package com.goku.gokubackend.application.controller;

import com.goku.gokubackend.application.controller.dto.CityDTO;
import com.goku.gokubackend.application.controller.dto.CustomerDTO;
import com.goku.gokubackend.application.controller.dto.StreetDTO;
import com.goku.gokubackend.domain.Customer;
import com.goku.gokubackend.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CustomerDTO create(@RequestBody CustomerDTO customerDTO) {
        Customer customer = mapToCustomer(customerDTO);
        customer = customerRepository.create(customer);
        return mapToDTO(customer);
    }

    @PutMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CustomerDTO update(@RequestBody CustomerDTO customerDTO) {
        Customer customer = mapToCustomer(customerDTO);
        customer = customerRepository.update(customer);
        return mapToDTO(customer);
    }

    private Customer mapToCustomer(CustomerDTO customerDTO) {
        return null;
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
                                        x.getStreetAddress().getCity().getName())))
                        .collect(Collectors.toList())
        );
    }

}
