package com.goku.gokubackend.application.controller;

import com.goku.gokubackend.application.jwt.JwtToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final JwtToken token;

    @Autowired
    public UserController(JwtToken token) {
        this.token = token;
    }

    @RequestMapping("hello")
    public HelloObj hello() {
        return new HelloObj("Hey!");
    }

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse login(@RequestBody UserCredentials credentials) {
        //TODO: check user credentials
        return new UserResponse(credentials.getUsername(), token(credentials.getUsername()));
    }

    private String token(String username) {
        return token.build(username, Arrays.asList("ROLE_USER"));
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
