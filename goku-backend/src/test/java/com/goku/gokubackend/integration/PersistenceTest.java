package com.goku.gokubackend.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.UUID;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestPersistenceConfig.class}, loader = AnnotationConfigContextLoader.class)
@Sql({"/schema.sql", "/test-data.sql"})
@Sql(value = {"/drop-tables-after-tests.sql"}, executionPhase = AFTER_TEST_METHOD)
public class PersistenceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testIt() {
        System.out.println("Hey!!" + jdbcTemplate);
        jdbcTemplate.update(
                "INSERT INTO GK_USER VALUES (?, ?, ?, ?)",
                UUID.randomUUID().toString(), "admin1", "admin1", "ROLE_USER,ROLE_ADMIN");
    }

    @Test
    public void testIt2() {
        System.out.println("Hey!!" + jdbcTemplate);
        jdbcTemplate.update(
                "INSERT INTO GK_USER VALUES (?, ?, ?, ?)",
                UUID.randomUUID().toString(), "admin11", "admin1", "ROLE_USER,ROLE_ADMIN");
    }


}
