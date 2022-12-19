package com.kowalczyk.studentclasses.aspect;

import com.kowalczyk.studentclasses.entity.UserData;
import com.kowalczyk.studentclasses.exception.AuthorizationException;
import com.kowalczyk.studentclasses.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class DataAuthorization {

    private final UserDataRepository userDataRepository;

    protected UserData getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userDataRepository.findByEmail(email)
                .orElseThrow(AuthorizationException::new);
    }

    protected Set<String> getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        if (authorities.isEmpty()) {
            throw new AuthorizationException();
        }
        return authorities;
    }

    protected void checkUserId(long id, UserData userData) {
        if (userData.getId() != id) {
            throw new AuthorizationException();
        }
    }
}
