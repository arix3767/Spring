package com.kowalczyk.studentclasses.configuration;

import com.kowalczyk.studentclasses.entity.UserData;
import com.kowalczyk.studentclasses.exception.UserNotFoundException;
import com.kowalczyk.studentclasses.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByEmail(username)
                .map(this::buildUserDetailsFrom)
                .orElseThrow(UserNotFoundException::new);
    }

    private UserDetails buildUserDetailsFrom(UserData userData) {
        return User.builder()
                .username(userData.getEmail())
                .password(userData.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }
}
