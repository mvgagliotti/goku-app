package com.goku.gokubackend.application;

import com.goku.gokubackend.application.jwt.JwtToken;
import com.goku.gokubackend.application.jwt.JwtToken.ParsedJwtToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class JwtTokenTest {

    private JwtToken jwtToken = new JwtToken("secret", 5000);

    @Test
    public void testCreatingToken() {
        String generatedToken = jwtToken.build("someUser", Arrays.asList("ROLE_USER"));
        Assertions.assertNotNull(generatedToken);
    }

    @Test
    public void testParseToken() {
        String generatedToken = jwtToken.build("someUSer", Arrays.asList("ROLE_USER"));
        ParsedJwtToken parsed = jwtToken.parse(generatedToken);
        Assertions.assertTrue(parsed.getAuthorities().isPresent());
        Assertions.assertEquals(Arrays.asList("ROLE_USER"), parsed.getAuthorities().get());
    }
}
