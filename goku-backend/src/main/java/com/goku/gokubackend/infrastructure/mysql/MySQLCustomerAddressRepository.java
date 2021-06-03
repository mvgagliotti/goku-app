package com.goku.gokubackend.infrastructure.mysql;

import com.goku.gokubackend.domain.Address;
import com.goku.gokubackend.domain.AddressInfo;
import com.goku.gokubackend.domain.CustomerAddress;
import com.goku.gokubackend.domain.repository.AddressRepository;
import com.goku.gokubackend.domain.repository.CustomerAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class MySQLCustomerAddressRepository implements CustomerAddressRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AddressRepository addressRepository;

    @Autowired
    public MySQLCustomerAddressRepository(JdbcTemplate jdbcTemplate, AddressRepository addressRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.addressRepository = addressRepository;
    }

    private final RowMapper<CustomerAddress> ROW_MAPPER = (rs, rowNum) -> new CustomerAddress(
            findAddressFrom(rs.getString("POSTAL_CODE")),
            new AddressInfo(rs.getInt("ADDRESS_NUMBER"), rs.getString("DESCRIPTION"))
    );

    private Address findAddressFrom(String postalCode) {
        return addressRepository.findById(postalCode);
    }

    @Override
    @Transactional
    public CustomerAddress create(String customerId, CustomerAddress customerAddress) {
        jdbcTemplate.update("INSERT INTO CUSTOMER_ADDRESS VALUES (?, ?, ?, ?)",
                customerId,
                customerAddress.getStreetAddress().getPostalCode(),
                customerAddress.getAddressInfo().getNumber(),
                customerAddress.getAddressInfo().getDescription());
        return customerAddress;
    }

    @Override
    public Optional<CustomerAddress> findByKey(String customerId, String postalCode) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM CUSTOMER_ADDRESS WHERE CS_ID = ? AND " +
                            "POSTAL_CODE = ?",
                    ROW_MAPPER, customerId, postalCode));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void update(String customerId, CustomerAddress customerAddress) {
        jdbcTemplate.update("UPDATE CUSTOMER_ADDRESS SET ADDRESS_NUMBER=?, DESCRIPTION=?" +
                        "WHERE CS_ID = ? AND POSTAL_CODE = ? ",
                customerAddress.getAddressInfo().getNumber(),
                customerAddress.getAddressInfo().getDescription(),
                customerId,
                customerAddress.getStreetAddress().getPostalCode());
    }

    @Override
    public List<CustomerAddress> findByCustomerId(String id) {
        return jdbcTemplate.query("SELECT * FROM CUSTOMER_ADDRESS WHERE CS_ID = ?", ROW_MAPPER, id);
    }
}
