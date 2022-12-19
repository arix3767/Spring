package com.kowalczyk.studentclasses.aspect;

import com.kowalczyk.studentclasses.annotation.StudentDataExtendedAuthorization;
import com.kowalczyk.studentclasses.entity.UserData;
import com.kowalczyk.studentclasses.enums.Role;
import com.kowalczyk.studentclasses.repository.UserDataRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
public class StudentDataAuthorizationAspect extends DataAuthorization {

    private static final Set<String> ALLOWED_ROLES = Set.of(Role.TEACHER.getAuthority(), Role.ADMIN.getAuthority());

    public StudentDataAuthorizationAspect(UserDataRepository userDataRepository) {
        super(userDataRepository);
    }

    @Before("@annotation(authorization) && args(id,..)")
    public void authorizeUser(StudentDataExtendedAuthorization authorization, long id) {
        UserData userData = getUserData();
        Set<String> authorities = getAuthorities();
        boolean isUserAllowed = authorities.stream()
                .anyMatch(ALLOWED_ROLES::contains);
        if (isUserAllowed) {
            return;
        }
        checkUserId(id, userData);
    }
}
