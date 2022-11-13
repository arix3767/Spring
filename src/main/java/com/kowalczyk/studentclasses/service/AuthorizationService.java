package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.entity.UserData;
import com.kowalczyk.studentclasses.exception.AuthorizationException;
import com.kowalczyk.studentclasses.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserDataRepository userDataRepository;

    public void authorizeUser(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserData userData = userDataRepository.findByEmail(email)
                .orElseThrow(AuthorizationException::new);
        if (userData.getId() != id) {
            throw new AuthorizationException();
        }
    }
}
