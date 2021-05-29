package com.goku.gokubackend.application.controller;

import com.goku.gokubackend.application.jwt.JwtToken;
import com.goku.gokubackend.domain.Customer;
import com.goku.gokubackend.domain.Roles;
import com.goku.gokubackend.domain.User;
import com.goku.gokubackend.domain.exception.InvalidCredentialsException;
import com.goku.gokubackend.domain.repository.CustomerRepository;
import com.goku.gokubackend.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private final JwtToken token;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public UserController(JwtToken token, UserRepository userRepository, CustomerRepository customerRepository) {
        this.token = token;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    @RequestMapping("hello")
    public HelloObj hello() {
        return new HelloObj("Hey!");
    }

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse login(@RequestBody UserCredentials credentials) {
        User user = userRepository
                .fetchByUsername(credentials.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException());
        if (!PASSWORD_ENCODER.matches(credentials.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        return new UserResponse(credentials.getUsername(), token(user));
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RegisterDTO register(@RequestBody RegisterDTO register) {

        User user = mapToUser(register);
        user = userRepository.create(user);

        customerRepository.create(new Customer(user.getId(), register.getName()));

        return new RegisterDTO(user.getId().get(), user.getUsername(), "******", register.getName());
    }

    private User mapToUser(RegisterDTO register) {
        return new User(Optional.empty(), register.getUsername(),
                PASSWORD_ENCODER.encode(register.getPassword()), new Roles("ROLE_USER"));
    }


    private String token(User user) {
        return token.build(user.getUsername(), user.getRoles().getValues());
    }

    public static class UserResponse {
        private final String user;
        private final String token;

        public UserResponse(String user, String token) {
            this.user = user;
            this.token = token;
        }

        public String getUser() {
            return user;
        }

        public String getToken() {
            return token;
        }
    }

    public static class UserCredentials {
        private final String username;
        private final String password;

        public UserCredentials(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class RegisterDTO extends UserCredentials {
        private final String name;
        private final String id;

        public RegisterDTO(String id, String username, String password, String name) {
            super(username, password);
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }

    public static class HelloObj {
        private final String message;

        public HelloObj(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
