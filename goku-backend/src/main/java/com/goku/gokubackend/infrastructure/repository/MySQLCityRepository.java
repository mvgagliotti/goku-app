package com.goku.gokubackend.infrastructure.repository;

import com.goku.gokubackend.domain.City;
import com.goku.gokubackend.domain.State;
import com.goku.gokubackend.domain.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MySQLCityRepository implements CityRepository, SpringEmptyResultHandler {

    private static final RowMapper<City> ROW_MAPPER = (rs, rowNum) -> new City(
            Optional.of(rs.getString("ID")),
            rs.getString("NAME"),
            new State(rs.getString("STATE_ABBREVIATION"), rs.getString("STATE_NAME"))
    );

    private final JdbcTemplate jdbc;

    @Autowired
    public MySQLCityRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public City create(City newInstance) {
        String id = newId();
        jdbc.update("INSERT INTO CITY VALUES (?, ?, ?, ?)",
                id,
                newInstance.getName(),
                newInstance.getState().getName(),
                newInstance.getState().getAbbreviation());
        return newInstance.withId(id);
    }

    @Override
    public City findById(String id) {
        return handleEmptyThrow(() -> jdbc.queryForObject("SELECT * FROM CITY WHERE ID = ?", ROW_MAPPER, id));
    }

    @Override
    public City update(City city) {
        if (city.getId() == null) {
            throw new RuntimeException("id must not be null");
        }
        jdbc.update("UPDATE CITY SET NAME=?, STATE_NAME=?, STATE_ABBREVIATION=? WHERE ID=?",
                city.getName(),
                city.getState().getName(),
                city.getState().getAbbreviation(),
                city.getId());
        return city;
    }

    public Optional<City> findByKey(City city) {
        Optional<City> foundCity = handleEmpty(() -> jdbc.queryForObject("SELECT * FROM CITY WHERE " +
                        "UPPER(NAME) = UPPER(?) AND UPPER(STATE_ABBREVIATION) = UPPER(?)",
                ROW_MAPPER,
                city.getName(),
                city.getState().getAbbreviation()));
        return foundCity;
    }
}
