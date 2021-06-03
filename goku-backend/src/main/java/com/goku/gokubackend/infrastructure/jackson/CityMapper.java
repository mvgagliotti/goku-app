package com.goku.gokubackend.infrastructure.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.goku.gokubackend.domain.City;
import com.goku.gokubackend.domain.State;

import static java.util.Optional.ofNullable;

public class CityMapper {

    public static ObjectMapper cityMapper() {
        Module cityDeserializer = JacksonUtils.createDeserializerFor(City.class, node -> new City(
                ofNullable(node.get("id").textValue()),
                node.get("name").textValue(),
                new State(
                        node.get("state").get("abbreviation").textValue(),
                        node.get("state").get("name").textValue()
                )));

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(cityDeserializer);

        return mapper;
    }
}
