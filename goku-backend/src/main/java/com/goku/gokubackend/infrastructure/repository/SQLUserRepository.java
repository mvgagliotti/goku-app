package com.goku.gokubackend.infrastructure.repository;

import com.goku.gokubackend.domain.User;
import com.goku.gokubackend.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SQLUserRepository implements UserRepository {

    private static final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(
            rs.getString("ID"),
            rs.getString("USERNAME"),
            rs.getString("PASSWORD"),
            rs.getString("ROLES")
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
                user.getRoles());
        return user.withId(id);
    }

    @Override
    public User fetchById(String id) {
        return jdbc.queryForObject("SELECT * FROM GK_USER WHERE ID = ?", ROW_MAPPER, id);
    }
}
