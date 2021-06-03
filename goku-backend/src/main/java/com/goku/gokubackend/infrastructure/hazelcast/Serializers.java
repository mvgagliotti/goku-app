package com.goku.gokubackend.infrastructure.hazelcast;

import com.goku.gokubackend.domain.City;

import static com.goku.gokubackend.infrastructure.jackson.CityMapper.cityMapper;

public class Serializers {

    public static BaseJacksonSerializer<City> citySerializer() {
        return new BaseJacksonSerializer<>(cityMapper(), City.class);
    }
}
