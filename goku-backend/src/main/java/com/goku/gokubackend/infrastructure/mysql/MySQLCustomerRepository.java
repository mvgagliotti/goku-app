package com.goku.gokubackend.infrastructure.mysql;

import com.goku.gokubackend.domain.Address;
import com.goku.gokubackend.domain.Addresses;
import com.goku.gokubackend.domain.Customer;
import com.goku.gokubackend.domain.CustomerAddress;
import com.goku.gokubackend.domain.repository.AddressRepository;
import com.goku.gokubackend.domain.repository.CustomerAddressRepository;
import com.goku.gokubackend.domain.repository.CustomerRepository;
import com.goku.gokubackend.infrastructure.SpringEmptyResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MySQLCustomerRepository implements CustomerRepository, SpringEmptyResultHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySQLCustomerRepository.class);

    private final RowMapper<Customer> ROW_MAPPER = (rs, rowNum) -> new Customer(
            Optional.of(rs.getString("ID")),
            rs.getString("NAME"),
            loadAddresses(rs.getString("ID"))
    );

    private final JdbcTemplate jdbcTemplate;
    private final AddressRepository addressRepository;
    private final CustomerAddressRepository customerAddressRepository;

    @Autowired
    public MySQLCustomerRepository(JdbcTemplate jdbcTemplate, AddressRepository addressRepository, CustomerAddressRepository customerAddressRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.addressRepository = addressRepository;
        this.customerAddressRepository = customerAddressRepository;
    }

    @Override
    public Customer findById(String id) {
        return handleEmptyThrow(() -> jdbcTemplate.queryForObject("SELECT * FROM CUSTOMER WHERE ID = ?", ROW_MAPPER, id));
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        customer.getId().orElseThrow(() -> new RuntimeException("Customer id is mandatory for update"));
        List<CustomerAddress> newAddresses = customer.getDeliveryAddresses()
                .get()
                .stream()
                .map(customerAddress -> {
                    Address newAddress = findOrCreateAddress(customerAddress);
                    return updateOrCreateCustomerAddress(customer, customerAddress, newAddress);
                }).collect(Collectors.toList());

        return new Customer(customer.getId(), customer.getName(), new Addresses(newAddresses));
    }

    private Address findOrCreateAddress(CustomerAddress customerAddress) {
        return addressRepository.findOrCreate(customerAddress.getStreetAddress());
    }

    private CustomerAddress updateOrCreateCustomerAddress(Customer customer, CustomerAddress customerAddress,
                                                          Address newAddress) {
        return customerAddressRepository.updateOrCreate(
                customer.getId().get(),
                new CustomerAddress(newAddress, customerAddress.getAddressInfo()));
    }

    @Override
    @Transactional
    public Customer create(Customer customer) {
        customer.getId().orElseThrow(() -> new RuntimeException("Customer id is mandatory"));
        jdbcTemplate.update("INSERT INTO CUSTOMER VALUES (?, ?)",
                customer.getId().get(),
                customer.getName());
        return customer;
    }

    private Addresses loadAddresses(String id) {
        List<CustomerAddress> list = customerAddressRepository.findByCustomerId(id);
        return new Addresses(list);
    }

}
