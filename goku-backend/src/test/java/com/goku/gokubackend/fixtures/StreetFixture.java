package com.goku.gokubackend.fixtures;

import com.goku.gokubackend.domain.Street;

import java.util.Optional;

public class StreetFixture {

    public static Street aStreet() {
        return new Street(Optional.empty(), "12061-571", "Av. Morumbi", "Morumbi", CityFixture.saoPaulo());
    }
}
