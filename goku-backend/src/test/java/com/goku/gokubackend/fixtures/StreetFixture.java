package com.goku.gokubackend.fixtures;

import com.goku.gokubackend.domain.Address;

import java.util.Optional;

public class StreetFixture {

    public static Address aStreet() {
        return new Address("12061-571", "Av. Morumbi", "Morumbi", CityFixture.saoPaulo());
    }
}
