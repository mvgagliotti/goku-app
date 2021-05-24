package com.goku.gokubackend.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class AdminCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminCreator.class);

    private final JdbcTemplate jdbcTemplate;

    public AdminCreator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void postConstruct() throws SQLException {

        jdbcTemplate.update(
                "INSERT INTO GK_USER VALUES (?, ?, ?, ?)",
                UUID.randomUUID().toString(), "admin", "admin", "ROLE_USER,ROLE_ADMIN");

    }
}
