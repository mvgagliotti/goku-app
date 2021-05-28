package com.goku.gokubackend.domain.repository;

import com.goku.gokubackend.domain.CustomerAddress;

import java.util.List;
import java.util.Optional;

public interface CustomerAddressRepository {
    CustomerAddress create(String customerId, CustomerAddress newInstance);
    Optional<CustomerAddress> findByKey(String customerId, String postalCode);
    void update(String customerId, CustomerAddress customerAddress);

    default CustomerAddress updateOrCreate(String customerId, CustomerAddress customerAddress) {
        Optional<CustomerAddress> existing = findByKey(customerId, customerAddress.getStreetAddress().getPostalCode());
        if (existing.isEmpty()) {
            return create(customerId, customerAddress);
        }
        update(customerId, customerAddress);
        return customerAddress;
    }

    List<CustomerAddress> findByCustomerId(String id);
}
