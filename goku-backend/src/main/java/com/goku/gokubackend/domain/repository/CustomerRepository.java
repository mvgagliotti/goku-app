package com.goku.gokubackend.domain.repository;

import com.goku.gokubackend.domain.Customer;

public interface CustomerRepository extends Repository<Customer> {
    Customer create(Customer customer);
    Customer findById(String id);
}
