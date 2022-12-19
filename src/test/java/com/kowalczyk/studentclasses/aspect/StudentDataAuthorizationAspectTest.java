package com.kowalczyk.studentclasses.aspect;

import com.kowalczyk.studentclasses.Utils.SecurityUtils;
import com.kowalczyk.studentclasses.annotation.StudentDataExtendedAuthorization;
import com.kowalczyk.studentclasses.entity.Admin;
import com.kowalczyk.studentclasses.entity.Student;
import com.kowalczyk.studentclasses.entity.Teacher;
import com.kowalczyk.studentclasses.enums.Role;
import com.kowalczyk.studentclasses.exception.AuthorizationException;
import com.kowalczyk.studentclasses.repository.UserDataRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@SpringJUnitConfig
public class StudentDataAuthorizationAspectTest {

    public static final String EMAIL = "janek";
    public static final String PASSWORD = "1234";
    private final MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic =
            mockStatic(SecurityContextHolder.class);

    @MockBean
    private UserDataRepository userDataRepository;

    @Mock
    private StudentDataExtendedAuthorization authorization;

    private StudentDataAuthorizationAspect authorizationAspect;

    @BeforeEach
    void setup() {
        authorizationAspect = new StudentDataAuthorizationAspect(userDataRepository);
    }

    @AfterEach
    void cleanup() {
        securityContextHolderMockedStatic.close();
    }

    @Test
    void authorizeStudent() {
        //given
        Student student = buildStudent();
        GrantedAuthority authority = new SimpleGrantedAuthority(Role.STUDENT.getAuthority());
        SecurityUtils.mockSecurityContextHolder(securityContextHolderMockedStatic, EMAIL, Collections.singleton(authority));
        Mockito.when(userDataRepository.findByEmail(EMAIL)).thenReturn(Optional.of(student));
        //when
        authorizationAspect.authorizeUser(authorization, student.getId());
        //then
        securityContextHolderMockedStatic.verify(SecurityContextHolder::getContext, times(2));
        Mockito.verify(userDataRepository).findByEmail(EMAIL);
    }

    private Student buildStudent() {
        return Student.builder()
                .id(1L)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    @Test
    void shouldNotAuthorizeUserIfUserDoesNotExist() {
        //given
        SecurityUtils.mockSecurityContextHolder(securityContextHolderMockedStatic, EMAIL);
        Mockito.when(userDataRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        //when
        assertThrows(AuthorizationException.class, () -> authorizationAspect.authorizeUser(authorization, buildStudent().getId()));
        //then
        securityContextHolderMockedStatic.verify(SecurityContextHolder::getContext);
        Mockito.verify(userDataRepository).findByEmail(EMAIL);
    }

    @Test
    void shouldNotAuthorizeUserIfInvalidId() {
        //given
        Student victimStudent = buildStudent();
        Student hackerStudent = buildStudent().toBuilder()
                .email("tomek")
                .id(2L)
                .build();
        SecurityUtils.mockSecurityContextHolder(securityContextHolderMockedStatic, hackerStudent.getEmail());
        Mockito.when(userDataRepository.findByEmail(hackerStudent.getEmail())).thenReturn(Optional.of(hackerStudent));
        //when
        assertThrows(AuthorizationException.class, () -> authorizationAspect.authorizeUser(authorization, victimStudent.getId()));
        //then
        securityContextHolderMockedStatic.verify(SecurityContextHolder::getContext, times(2));
        Mockito.verify(userDataRepository).findByEmail(hackerStudent.getEmail());
    }

    @Test
    void authorizeTeacher() {
        //given
        Teacher teacher = buildTeacher();
        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(teacher.getRole().getAuthority()));
        SecurityUtils.mockSecurityContextHolder(securityContextHolderMockedStatic, EMAIL, authorities);
        Mockito.when(userDataRepository.findByEmail(EMAIL)).thenReturn(Optional.of(teacher));
        //when
        authorizationAspect.authorizeUser(authorization, teacher.getId());
        //then
        securityContextHolderMockedStatic.verify(SecurityContextHolder::getContext, times(2));
        Mockito.verify(userDataRepository).findByEmail(EMAIL);
    }

    private Teacher buildTeacher() {
        return Teacher.builder()
                .id(2L)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    @Test
    void shouldNotAuthorizeTeacherIfTeacherDoesNotExist() {
        //given
        SecurityUtils.mockSecurityContextHolder(securityContextHolderMockedStatic, EMAIL);
        Mockito.when(userDataRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        //when
        assertThrows(AuthorizationException.class, () -> authorizationAspect.authorizeUser(authorization, buildTeacher().getId()));
        //then
        securityContextHolderMockedStatic.verify(SecurityContextHolder::getContext);
        Mockito.verify(userDataRepository).findByEmail(EMAIL);
    }

    @Test
    void authorizeAdmin() {
        //given
        Admin admin = buildAdmin();
        GrantedAuthority authority = new SimpleGrantedAuthority(Role.ADMIN.getAuthority());
        SecurityUtils.mockSecurityContextHolder(securityContextHolderMockedStatic, EMAIL, Collections.singleton(authority));
        Mockito.when(userDataRepository.findByEmail(EMAIL)).thenReturn(Optional.of(admin));
        //when
        authorizationAspect.authorizeUser(authorization, admin.getId());
        //then
        securityContextHolderMockedStatic.verify(SecurityContextHolder::getContext, times(2));
        Mockito.verify(userDataRepository).findByEmail(EMAIL);
    }

    private Admin buildAdmin() {
        return Admin.builder()
                .id(3L)
                .password(PASSWORD)
                .email(EMAIL)
                .build();
    }

    @Test
    void shouldNotAuthorizeAdminIfAdminDoesNotExist() {
        //given
        SecurityUtils.mockSecurityContextHolder(securityContextHolderMockedStatic, EMAIL);
        Mockito.when(userDataRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        //when
        assertThrows(AuthorizationException.class, () -> authorizationAspect.authorizeUser(authorization, buildAdmin().getId()));
        //then
        securityContextHolderMockedStatic.verify(SecurityContextHolder::getContext);
        Mockito.verify(userDataRepository).findByEmail(EMAIL);
    }
}
