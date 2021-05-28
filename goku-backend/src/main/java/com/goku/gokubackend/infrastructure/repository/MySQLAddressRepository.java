package com.goku.gokubackend.infrastructure.repository;

import com.goku.gokubackend.domain.Address;
import com.goku.gokubackend.domain.City;
import com.goku.gokubackend.domain.repository.AddressRepository;
import com.goku.gokubackend.domain.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MySQLAddressRepository implements AddressRepository, SpringEmptyResultHandler {

    private final RowMapper<Address> ROW_MAPPER = (rs, rowNum) -> new Address(
            rs.getString("POSTAl_CODE"),
            rs.getString("STREET_NAME"),
            rs.getString("DISTRICT"),
            findCityById(rs.getString("CITY_ID"))
    );

    private final CityRepository cityRepository;
    private final JdbcTemplate jdbc;

    @Autowired
    public MySQLAddressRepository(CityRepository cityRepository, JdbcTemplate jdbc) {
        this.cityRepository = cityRepository;
        this.jdbc = jdbc;
    }

    private City findCityById(String id) {
        return cityRepository.findById(id);
    }

    @Override
    public Address create(Address newInstance) {
        City city = cityRepository.findOrCreate(newInstance.getCity());
        jdbc.update("INSERT INTO ADDRESS VALUES(?, ?, ?, ?)",
                newInstance.getPostalCode(),
                newInstance.getStreetName(),
                newInstance.getDistrict(),
                city.getId().get());
        return new Address(newInstance.getPostalCode(), newInstance.getStreetName(), newInstance.getDistrict(), city);
    }

    @Override
    public Address findById(String id) {
        return handleEmpty(() -> jdbc.queryForObject("SELECT * FROM ADDRESS WHERE POSTAL_CODE = ?", ROW_MAPPER, id));
    }

    @Override
    public Address update(Address address) {
        address.getId().orElseThrow(() -> new RuntimeException("id must not be null"));
        address.getCity().getId().orElseThrow(() -> new RuntimeException("City id must not be null"));

        jdbc.update("UPDATE ADDRESS SET STREET_NAME=?, DISTRICT=?, CITY_ID=? WHERE ID=?",
                address.getStreetName(),
                address.getDistrict(),
                address.getCity().getId().get());
        return address;
    }

    @Override
    public Optional<Address> findByPostalCode(String postalCode) {
        try {
            return Optional.of(jdbc.queryForObject("SELECT * FROM ADDRESS WHERE POSTAL_CODE = ?", ROW_MAPPER, postalCode));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
