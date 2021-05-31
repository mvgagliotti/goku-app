package com.goku.gokubackend.application.auth;

import com.goku.gokubackend.domain.User;
import com.goku.gokubackend.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomerAccessChecker {

    @Autowired
    private final UserRepository userRepository;

    public CustomerAccessChecker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void check(String id) {
        User user = userRepository.findById(id);
        String userFromContext = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (!user.getUsername().equals(userFromContext)) {
            throw new ForbiddenAccessException();
        }
    }
}
