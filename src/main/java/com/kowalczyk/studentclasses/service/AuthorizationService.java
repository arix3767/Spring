package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.entity.UserData;
import com.kowalczyk.studentclasses.enums.Role;
import com.kowalczyk.studentclasses.exception.AuthorizationException;
import com.kowalczyk.studentclasses.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserDataRepository userDataRepository;

    public void authorizeUser(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserData userData = userDataRepository.findByEmail(email)
                .orElseThrow(AuthorizationException::new);
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        if (!authorities.contains(Role.STUDENT.getAuthority())) {
            return;
        }
        if (userData.getId() != id) {
            throw new AuthorizationException();
        }
    }
}
