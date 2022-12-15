package com.kowalczyk.studentclasses.aspect;

import com.kowalczyk.studentclasses.annotation.TeacherDataExtendedAuthorization;
import com.kowalczyk.studentclasses.entity.UserData;
import com.kowalczyk.studentclasses.enums.Role;
import com.kowalczyk.studentclasses.exception.AuthorizationException;
import com.kowalczyk.studentclasses.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class TeacherDataAuthorizationAspect {

    private final UserDataRepository userDataRepository;

    @Before("@annotation(authorization) && args(id,..)")
    public void authorizeUser(TeacherDataExtendedAuthorization authorization, long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserData userData = userDataRepository.findByEmail(email)
                .orElseThrow(AuthorizationException::new);
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        if (authorities.isEmpty()) {
            throw new AuthorizationException();
        }
        if (authorities.contains(Role.ADMIN.getAuthority())) {
            return;
        }
        if (userData.getId() != id) {
            throw new AuthorizationException();
        }

    }
}
