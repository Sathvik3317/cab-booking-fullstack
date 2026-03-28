package com.CabBooking.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // This configuration allows all requests without login
    // This is only for learning and testing using Postman
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())      // Disable CSRF for API testing
            .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll() // Allow all APIs without authentication
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
