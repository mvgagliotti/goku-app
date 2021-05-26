package com.goku.gokubackend.domain;

import com.goku.gokubackend.domain.exception.CustomerException;
import com.goku.gokubackend.domain.utils.ValidationUtils;

import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class Customer implements Entity {
    private final Optional<String> id;
    private final String name;
    private final Addresses deliveryAddresses;

    public Customer(Optional<String> id, String name, Addresses deliveryAddresses) {
        ValidationUtils.notNull(id, () -> new CustomerException("id cannot be null"));
        ValidationUtils.notNull(name, () -> new CustomerException("name cannot be null"));
        ValidationUtils.notNull(deliveryAddresses, () -> new CustomerException("deliveryAddresses cannot be null"));
        this.id = id;
        this.name = name;
        this.deliveryAddresses = deliveryAddresses;
    }

    public Customer(Optional<String> id, String name) {
        this(id, name, new Addresses(emptyList()));
    }

    public Optional<String> getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Addresses getDeliveryAddresses() {
        return deliveryAddresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) && name.equals(customer.name) && deliveryAddresses.equals(customer.deliveryAddresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, deliveryAddresses);
    }
}
