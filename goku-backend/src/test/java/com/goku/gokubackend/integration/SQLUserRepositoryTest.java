package com.goku.gokubackend.integration;

import com.goku.gokubackend.domain.User;
import com.goku.gokubackend.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestPersistenceConfig.class}, loader = AnnotationConfigContextLoader.class)
@Sql({"/schema.sql", "/test-data.sql"})
@Sql(value = {"/drop-tables-after-tests.sql"}, executionPhase = AFTER_TEST_METHOD)
public class SQLUserRepositoryTest {

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
        User user = new User(null, "Admin2", "XXX", "ROLE_ADMIN");
        User newUser = userRepository.create(user);
        Assertions.assertNotNull(newUser.getId());
    }
}
