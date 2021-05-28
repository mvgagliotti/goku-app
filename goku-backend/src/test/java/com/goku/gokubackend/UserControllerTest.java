package com.goku.gokubackend;

import com.goku.gokubackend.application.controller.UserController;
import com.goku.gokubackend.application.controller.UserController.UserResponse;
import com.goku.gokubackend.application.jwt.JwtToken;
import com.goku.gokubackend.domain.repository.UserRepository;
import com.goku.gokubackend.fixtures.UserFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private JwtToken jwtToken = mock(JwtToken.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private UserController controller = new UserController(jwtToken, userRepository);

    @Test
    public void testLogin() {

        when(jwtToken.build(eq("user@email.com"), eq(Arrays.asList("ROLE_USER")))).thenReturn("a_token");
        when(userRepository.fetchByUsername("user@email.com")).thenReturn(UserFixture.aUser());

        UserResponse response =
                controller.login(new UserController.UserCredentials("user@email.com", "passwrd"));

        Assertions.assertEquals("user@email.com", response.getUser());
        Assertions.assertEquals("a_token", response.getToken());
    }

}
