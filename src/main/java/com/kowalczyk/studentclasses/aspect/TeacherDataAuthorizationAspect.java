package com.kowalczyk.studentclasses.aspect;

import com.kowalczyk.studentclasses.annotation.TeacherDataExtendedAuthorization;
import com.kowalczyk.studentclasses.entity.UserData;
import com.kowalczyk.studentclasses.enums.Role;
import com.kowalczyk.studentclasses.repository.UserDataRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
public class TeacherDataAuthorizationAspect extends DataAuthorization {

    public TeacherDataAuthorizationAspect(UserDataRepository userDataRepository) {
        super(userDataRepository);
    }

    @Before("@annotation(authorization) && args(id,..)")
    public void authorizeUser(TeacherDataExtendedAuthorization authorization, long id) {
        UserData userData = getUserData();
        Set<String> authorities = getAuthorities();
        if (authorities.contains(Role.ADMIN.getAuthority())) {
            return;
        }
        checkUserId(id, userData);
    }
}
