package com.kowalczyk.studentclasses.aspect;

import com.kowalczyk.studentclasses.annotation.StudentDataExtendedAuthorization;
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
public class StudentDataAuthorizationAspect {

    private final UserDataRepository userDataRepository;

    private static final Set<String> ALLOWED_ROLES = Set.of(Role.TEACHER.getAuthority(), Role.ADMIN.getAuthority());

    @Before("@annotation(authorization) && args(id,..)")
    public void authorizeUser(StudentDataExtendedAuthorization authorization, long id) {
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
        boolean isUserAllowed = authorities.stream()
                .anyMatch(ALLOWED_ROLES::contains);
        if (isUserAllowed) {
            return;
        }
        if (userData.getId() != id) {
            throw new AuthorizationException();
        }
    }
}
