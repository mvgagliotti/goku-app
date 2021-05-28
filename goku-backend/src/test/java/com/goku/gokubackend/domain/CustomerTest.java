package com.goku.gokubackend.domain;

import com.goku.gokubackend.domain.exception.CustomerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerTest {

    @Test
    public void testNewCustomerBasicValidation() {
        assertEquals(
                "id cannot be null",
                assertThrows(CustomerException.class,
                    () -> new Customer(null, "Customer1", new Addresses(emptyList()))).getMessage());
        assertEquals(
                "name cannot be null",
                assertThrows(CustomerException.class,
                    () -> new Customer(Optional.of("1"), null, new Addresses(emptyList()))).getMessage());
        assertEquals(
                "deliveryAddresses cannot be null",
                assertThrows(CustomerException.class,
                    () -> new Customer(Optional.of("1"), "Customer1", null)).getMessage());
    }

    @Test
    public void testCustomerWithoutAnAddress() {
        Customer aCustomer = new Customer(Optional.of("1"), "Fulano");
        Assertions.assertEquals("1", aCustomer.getId().get());
        Assertions.assertEquals("Fulano", aCustomer.getName());
        Assertions.assertTrue(aCustomer.getDeliveryAddresses().get().isEmpty());
    }
}
