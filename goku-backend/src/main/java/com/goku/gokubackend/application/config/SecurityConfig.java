package com.goku.gokubackend.application.config;

import com.goku.gokubackend.application.JWTAuthorizationFilter;
import com.goku.gokubackend.application.jwt.JwtToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final int TEN_MINUTES = 600000;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(token()), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers( "/login").permitAll()
                .antMatchers("/hello").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated();
    }

    @Bean
    public JwtToken token() {
        return new JwtToken("myJWT", "mySecretKey", TEN_MINUTES);
    }
}
