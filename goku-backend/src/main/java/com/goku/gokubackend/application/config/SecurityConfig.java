package com.goku.gokubackend.application.config;

import com.goku.gokubackend.application.auth.filter.JWTAuthorizationFilter;
import com.goku.gokubackend.application.auth.jwt.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static java.lang.Integer.*;

@Configuration
@EnableWebSecurity
@PropertySource("config.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final int TEN_MINUTES = 600000;

    @Autowired
    private Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(token()), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/admin-register").hasRole("ADMIN")
                .antMatchers("/customer").hasAnyRole("USER", "ADMIN")
                .antMatchers("/address").hasRole("ADMIN")
                .anyRequest().authenticated();
    }

    @Bean
    public JwtToken token() {
        return new JwtToken(env.getProperty("jwt.id"),
                env.getProperty("jwt.secret-key"),
                valueOf(env.getProperty("jwt.expiration")));
    }
}
