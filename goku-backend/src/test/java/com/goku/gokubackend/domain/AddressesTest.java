package com.goku.gokubackend.domain;

import com.goku.gokubackend.domain.exception.DuplicatedAddressException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static com.goku.gokubackend.fixtures.StreetFixture.aStreet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressesTest {

    @Test
    public void testAddingNewAddress() {
        Customer customer = new Customer(Optional.of("1"), "TestCustomer");

        customer.getDeliveryAddresses()
                .add(new CustomerAddress(aStreet(), new AddressInfo(104, "Apto 112")));

        assertEquals(
                //new object to test equals & hashcode
                new CustomerAddress(aStreet(), new AddressInfo(104, "Apto 112")),
                customer.getDeliveryAddresses().get().get(0));
    }

    @Test
    public void testUpdatingAddress() {
        CustomerAddress address = new CustomerAddress(aStreet(), new AddressInfo(104, "Apto 112"));

        Customer customer = new Customer(Optional.of("1"), "TestCustomer");

        customer.getDeliveryAddresses().add(address);

        CustomerAddress newAddress = new CustomerAddress(aStreet(), new AddressInfo(204, "Apto 112"));
        customer.getDeliveryAddresses().update(newAddress);

        Assertions.assertEquals(1, customer.getDeliveryAddresses().get().size());
        Assertions.assertTrue(customer.getDeliveryAddresses().get().contains(newAddress));
    }


    @Test
    public void preventAddressDuplication() {
        CustomerAddress address1 = new CustomerAddress(aStreet(), new AddressInfo(104, "Apto 112"));
        CustomerAddress address2 = new CustomerAddress(aStreet(), new AddressInfo(104, "Apto 112"));

        Customer customer = new Customer(Optional.of("1"), "TestCustomer");

        customer.getDeliveryAddresses().add(address1);
        assertThrows(DuplicatedAddressException.class, () -> customer.getDeliveryAddresses().add(address2));
    }

    @Test
    public void preventAddressDuplicationViaConstructor() {
        CustomerAddress address1 = new CustomerAddress(aStreet(), new AddressInfo(104, "Rua X"));
        CustomerAddress address2 = new CustomerAddress(aStreet(), new AddressInfo(104, "Rua X"));
        assertThrows(DuplicatedAddressException.class, () -> new Addresses(Arrays.asList(address1, address2)));
    }

}
