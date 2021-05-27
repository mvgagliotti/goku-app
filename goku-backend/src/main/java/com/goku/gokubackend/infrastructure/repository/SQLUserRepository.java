package com.goku.gokubackend.infrastructure.repository;

import com.goku.gokubackend.domain.Roles;
import com.goku.gokubackend.domain.User;
import com.goku.gokubackend.domain.repository.UserRepository;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class SQLUserRepository implements UserRepository {

    private static final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(
            rs.getString("ID"),
            rs.getString("USERNAME"),
            rs.getString("PASSWORD"),
            new Roles(rs.getString("ROLES").split(","))
    );

    private final JdbcTemplate jdbc;

    @Autowired
    public SQLUserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public User create(User user) {
        String id = newId();
        jdbc.update("INSERT INTO GK_USER VALUES (?, ?, ?, ?)",
                id,
                user.getUsername(),
                user.getPassword(),
                user.getRoles().asCSV());
        return user.withId(id);
    }

    @Override
    public User fetchById(String id) {
        return handleEmpty(() -> jdbc.queryForObject("SELECT * FROM GK_USER WHERE ID = ?", ROW_MAPPER, id));
    }

    @Override
    public User fetchByUsername(String username) {
        return handleEmpty(() -> jdbc.queryForObject("SELECT * FROM GK_USER WHERE USERNAME = ?", ROW_MAPPER, username));
    }

    private <T> T handleEmpty(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (EmptyResultDataAccessException ex) {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void update(User user) {
        if (user.getId() == null) {
            throw new RuntimeException("id must not be null");
        }
        jdbc.update("UPDATE GK_USER SET USERNAME=?, PASSWORD=?, ROLES=? WHERE ID=?",
                user.getUsername(),
                user.getPassword(),
                user.getRoles().asCSV(),
                user.getId());
    }

}
