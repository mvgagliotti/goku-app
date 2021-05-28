package com.goku.gokubackend.fixtures;

import com.goku.gokubackend.domain.*;

import java.util.Collections;
import java.util.Optional;

public class CustomerFixture {

    public static Customer aCostumer(Optional<String> id) {
        Addresses ad = new Addresses(Collections.emptyList());

        City city = new City(Optional.of("1"), "Taubaté", new State("SP", "São Paulo"));
        Address st = new Address("12121", "Rua X", "Bairro X", city);
        CustomerAddress ca = new CustomerAddress(st, new AddressInfo(204, ""));

        ad.add(ca);

        return new Customer(id, "Zé", ad);
    }
}
