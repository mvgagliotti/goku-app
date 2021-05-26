package com.goku.gokubackend.domain;

import com.goku.gokubackend.domain.exception.AddressUpdateException;
import com.goku.gokubackend.domain.exception.DuplicatedAddressException;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Addresses {
    private final List<CustomerAddress> list;

    public Addresses(List<CustomerAddress> list) {
        Validate.notNull(list, "List of addresses cannot be null");
        this.list = new ArrayList<>();
        list.stream().forEach(this::add);
    }

    public List<CustomerAddress> get() {
        return Collections.unmodifiableList(list);
    }

    public void add(CustomerAddress address) {
        validateDuplication(address);
        list.add(address);
    }

    public void update(CustomerAddress address) {
        CustomerAddress old = list.stream()
                .filter(x -> x.getStreet().equals(address.getStreet()))
                .findAny()
                .orElseThrow(() -> new AddressUpdateException("Address not found on list: " + address.toString()));

        list.set(list.indexOf(old), address);
    }

    private void validateDuplication(CustomerAddress address) {
        if (list.stream().filter(address::equals).findFirst().isPresent()) {
            throw new DuplicatedAddressException("Duplicated address: " + address.toString());
        }
    }

}
