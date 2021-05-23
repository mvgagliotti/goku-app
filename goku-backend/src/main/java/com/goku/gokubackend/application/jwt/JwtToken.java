package com.goku.gokubackend.application.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

public class JwtToken {

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    private final String id;
    private final String secretKey;
    private final long expiration;

    public JwtToken(String id, String secretKey, long expiration) {
        this.id = id;
        this.secretKey = secretKey;
        this.expiration = expiration;
    }

    public JwtToken(String secretKey, long expiration) {
        this(UUID.randomUUID().toString(), secretKey, expiration);
    }

    public String build(String username, Collection<String> authorities) {
        return "Bearer " + Jwts
                .builder()
                .setId(id)
                .setSubject(username)
                .claim("authorities", authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .compact();
    }

    public ParsedJwtToken parse(String header) {

        validate(header);
        Claims body = parseAndExtractBody(header);
        Optional<List<String>> authorities = Optional.of((List<String>) body.get("authorities"));


        return new ParsedJwtToken(body.getSubject(), authorities);
    }

    private Claims parseAndExtractBody(String jwtToken) {
        final Claims body = Jwts
                .parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(jwtToken.replace(PREFIX, ""))
                .getBody();
        return body;
    }

    private void validate(String header) {
        if (header == null) {
            throw new JwtParseException("Missing header");
        }

        if (!header.startsWith(PREFIX)) {
            throw new JwtParseException("Missing token prefix");
        }
    }

    public static class ParsedJwtToken {
        private final Optional<List<String>> authorities;
        private final String subject;

        public ParsedJwtToken(String subject, Optional<List<String>> authorities) {
            this.subject = subject;
            this.authorities = authorities;
        }

        public String getSubject() {
            return subject;
        }

        public Optional<List<String>> getAuthorities() {
            return authorities;
        }
    }
}
