package com.goku.gokubackend.infrastructure;

import com.goku.gokubackend.domain.Roles;
import com.goku.gokubackend.domain.User;
import com.goku.gokubackend.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class MySQLUserRepository implements UserRepository, SpringEmptyResultHandler {

    private static final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(
            Optional.of(rs.getString("ID")),
            rs.getString("USERNAME"),
            rs.getString("PASSWORD"),
            new Roles(rs.getString("ROLES").split(","))
    );

    private final JdbcTemplate jdbc;

    @Autowired
    public MySQLUserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @Transactional
    public User create(User newInstance) {
        String id = newId();
        jdbc.update("INSERT INTO GK_USER VALUES (?, ?, ?, ?)",
                id,
                newInstance.getUsername(),
                newInstance.getPassword(),
                newInstance.getRoles().asCSV());
        return newInstance.withId(id);
    }

    @Override
    public User findById(String id) {
        return fetchById(id);
    }

    @Override
    public User fetchById(String id) {
        return handleEmptyThrow(() -> jdbc.queryForObject("SELECT * FROM GK_USER WHERE ID = ?", ROW_MAPPER, id));
    }

    @Override
    public Optional<User> fetchByUsername(String username) {
        return handleEmpty(() -> jdbc.queryForObject("SELECT * FROM GK_USER WHERE USERNAME = ?", ROW_MAPPER, username));
    }

    @Override
    @Transactional
    public User update(User user) {
        if (user.getId() == null) {
            throw new RuntimeException("id must not be null");
        }
        jdbc.update("UPDATE GK_USER SET USERNAME=?, PASSWORD=?, ROLES=? WHERE ID=?",
                user.getUsername(),
                user.getPassword(),
                user.getRoles().asCSV(),
                user.getId().get());
        return user;
    }

}
