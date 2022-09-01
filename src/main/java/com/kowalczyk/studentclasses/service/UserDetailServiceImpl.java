package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.entity.UserData;
import com.kowalczyk.studentclasses.exception.UserNotFoundException;
import com.kowalczyk.studentclasses.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .map(this::buildUserDetailsFrom)
                .orElseThrow(UserNotFoundException::new);
    }

    private UserDetails buildUserDetailsFrom(UserData userData) {
        return User.builder()
                .username(userData.getEmail())
                .password(userData.getPassword())
                .build();
    }
}
