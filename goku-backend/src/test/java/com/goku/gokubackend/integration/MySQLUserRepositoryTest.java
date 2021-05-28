package com.goku.gokubackend.integration;

import com.goku.gokubackend.domain.Roles;
import com.goku.gokubackend.domain.User;
import com.goku.gokubackend.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class MySQLUserRepositoryTest extends DatabaseIntegrationBaseTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFetchingByIt() {
        User user = userRepository.fetchById("1");
        System.out.println(user);
    }

    @Test
    public void testCreatingNewUser() {
        User user = new User(null, "Admin2", "XXX", new Roles("ROLE_ADMIN"));
        User newUser = userRepository.create(user);
        Assertions.assertNotNull(newUser.getId());
    }

    @Test
    public void testUpdatingUser() {
        User user = new User(null, "Admin2", "XXX", new Roles("ROLE_ADMIN"));
        User newUser = userRepository.create(user);
        Assertions.assertNotNull(newUser.getId());
        User updated = new User(newUser.getId(), newUser.getUsername(), newUser.getPassword(), new Roles("ROLE_ADMIN", "ROLE_USER"));
        userRepository.update(updated);
        updated = userRepository.fetchById(updated.getId().get());
        Assertions.assertEquals(new Roles("ROLE_ADMIN","ROLE_USER"), updated.getRoles());
    }

}
