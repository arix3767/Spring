package com.kowalczyk.studentclasses.configuration;

import com.kowalczyk.studentclasses.configuration.securitybuilders.DevHttpSecurityBuilder;
import com.kowalczyk.studentclasses.configuration.securitybuilders.HttpSecurityBuilder;
import com.kowalczyk.studentclasses.configuration.securitybuilders.HttpSecurityDirector;
import com.kowalczyk.studentclasses.configuration.securitybuilders.ProdHttpSecurityBuilder;
import com.kowalczyk.studentclasses.entity.UserData;
import com.kowalczyk.studentclasses.exception.UserNotFoundException;
import com.kowalczyk.studentclasses.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Profile(Profiles.DEV)
    @Bean
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        HttpSecurityBuilder builder = new DevHttpSecurityBuilder();
        return securityFilterChain(httpSecurity, builder);
    }

    private SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HttpSecurityBuilder builder) throws Exception {
        HttpSecurityDirector director = new HttpSecurityDirector(builder);
        httpSecurity = director.construct(httpSecurity);
        return httpSecurity.build();
    }

    @Profile(Profiles.PROD)
    @Bean
    public SecurityFilterChain prodSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        HttpSecurityBuilder builder = new ProdHttpSecurityBuilder();
        return securityFilterChain(httpSecurity, builder);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByEmail(username)
                .map(this::buildUserDetailsFrom)
                .orElseThrow(UserNotFoundException::new);
    }

    private UserDetails buildUserDetailsFrom(UserData userData) {
        String role = userData.getRole().getAuthority();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return User.builder()
                .username(userData.getEmail())
                .password(userData.getPassword())
                .authorities(authority)
                .build();
    }
}
