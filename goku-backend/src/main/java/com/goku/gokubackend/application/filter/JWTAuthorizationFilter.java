package com.goku.gokubackend.application.filter;

import com.goku.gokubackend.application.jwt.JwtParseException;
import com.goku.gokubackend.application.jwt.JwtToken;
import com.goku.gokubackend.application.jwt.JwtToken.ParsedJwtToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final JwtToken jwtToken;

    public JWTAuthorizationFilter(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (request.getHeader(HEADER) == null) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }

        try {
            ParsedJwtToken token = jwtToken.parse(request.getHeader(HEADER));

            if (token.getAuthorities().isPresent()) {
                setupSecurityContextHolder(token);
            } else {
                SecurityContextHolder.clearContext();
            }

            chain.doFilter(request, response);
        } catch (JwtParseException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getRealCauseMessage());
        }
    }

    private void setupSecurityContextHolder(ParsedJwtToken token) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(token.getSubject(), null, getAuthoritiesList(token));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private List<SimpleGrantedAuthority> getAuthoritiesList(ParsedJwtToken token) {
        return token
                .getAuthorities()
                .get()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}