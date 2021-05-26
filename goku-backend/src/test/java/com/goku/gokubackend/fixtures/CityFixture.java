package com.goku.gokubackend.fixtures;

import com.goku.gokubackend.domain.City;
import com.goku.gokubackend.domain.State;

import java.util.Optional;

public class CityFixture {

    public static City saoPaulo() {
        return new City(Optional.of("1"), "São Paulo", new State("SP", "São Paulo"));
    }

    public static City londrina() {
        return new City(Optional.of("2"), "Londrina", new State("PR", "Paraná"));
    }

}
