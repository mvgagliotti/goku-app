package com.goku.gokubackend.integration;

import com.goku.gokubackend.domain.Customer;
import com.goku.gokubackend.domain.User;
import com.goku.gokubackend.domain.repository.CustomerRepository;
import com.goku.gokubackend.domain.repository.UserRepository;
import com.goku.gokubackend.fixtures.CustomerFixture;
import com.goku.gokubackend.fixtures.UserFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MySQLCustomerRepositoryTest extends DatabaseIntegrationBaseTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreatingACostumer() {
        User user = userRepository.create(UserFixture.aUser());
        Customer customer = CustomerFixture.aCostumer(user.getId());
        customerRepository.create(customer);
        customer = customerRepository.findById(customer.getId().get());
        Assertions.assertNotNull(customer);
    }

    @Test
    public void testUpdatingCustomerWithAnAddress() {
        User user = userRepository.create(UserFixture.aUser());
        Customer customer = CustomerFixture.aCostumer(user.getId());

        customerRepository.create(customer);
        Customer updated = customerRepository.update(customer);

        Assertions.assertEquals(1, updated.getDeliveryAddresses().get().size());
        Customer loaded = customerRepository.findById(updated.getId().get());
        Assertions.assertEquals(1, loaded.getDeliveryAddresses().get().size());
    }
}
