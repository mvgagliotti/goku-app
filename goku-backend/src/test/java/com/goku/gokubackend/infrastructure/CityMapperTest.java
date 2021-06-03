package com.goku.gokubackend.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goku.gokubackend.domain.City;
import com.goku.gokubackend.domain.State;
import com.goku.gokubackend.infrastructure.jackson.CityMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class CityMapperTest {

    @Test
    public void testCitySerialization() throws JsonProcessingException {

        ObjectMapper mapper = CityMapper.cityMapper();

        City city = new City(Optional.empty(), "Taubaté", new State("SP", "São Paulo"));

        String value = mapper.writeValueAsString(city);

        City deserialized = mapper.readValue(value, City.class);

        Assertions.assertEquals(city, deserialized);

    }
}
