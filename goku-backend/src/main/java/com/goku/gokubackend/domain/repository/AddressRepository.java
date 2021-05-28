package com.goku.gokubackend.domain.repository;

import com.goku.gokubackend.domain.Address;

import java.util.Optional;

public interface AddressRepository extends Repository<Address> {

    Optional<Address> findByPostalCode(String postalCode);
}
