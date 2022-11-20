package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.Utils.SecurityUtils;
import com.kowalczyk.studentclasses.entity.UserData;
import com.kowalczyk.studentclasses.exception.AuthorizationException;
import com.kowalczyk.studentclasses.repository.UserDataRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

@SpringJUnitConfig
public class AuthorizationServiceTest {

    public static final String EMAIL = "janek";
    public static final String PASSWORD = "1234";
    private final MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic =
            mockStatic(SecurityContextHolder.class);

    @MockBean
    private UserDataRepository userDataRepository;

    private AuthorizationService authorizationService;

    @BeforeEach
    void setup() {
        authorizationService = new AuthorizationService(userDataRepository);
    }

    @AfterEach
    void cleanup() {
        securityContextHolderMockedStatic.close();
    }

    @Test
    void authorizeUser() {
        //given
        UserData user = buildUser();
        SecurityUtils.mockSecurityContextHolder(securityContextHolderMockedStatic, EMAIL);
        Mockito.when(userDataRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        //when
        authorizationService.authorizeUser(user.getId());
        //then
        securityContextHolderMockedStatic.verify(SecurityContextHolder::getContext);
        Mockito.verify(userDataRepository).findByEmail(EMAIL);
    }

    private UserData buildUser() {
        return UserData.builder()
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
        assertThrows(AuthorizationException.class, () -> authorizationService.authorizeUser(buildUser().getId()));
        //then
        securityContextHolderMockedStatic.verify(SecurityContextHolder::getContext);
        Mockito.verify(userDataRepository).findByEmail(EMAIL);
    }

    @Test
    void shouldNotAuthorizeUserIfInvalidId() {
        //given
        UserData victimUser = buildUser();
        UserData hackerUser = buildUser().toBuilder()
                .email("tomek")
                .id(2L)
                .build();
        SecurityUtils.mockSecurityContextHolder(securityContextHolderMockedStatic, hackerUser.getEmail());
        Mockito.when(userDataRepository.findByEmail(hackerUser.getEmail())).thenReturn(Optional.of(hackerUser));
        //when
        assertThrows(AuthorizationException.class, () -> authorizationService.authorizeUser(victimUser.getId()));
        //then
        securityContextHolderMockedStatic.verify(SecurityContextHolder::getContext);
        Mockito.verify(userDataRepository).findByEmail(hackerUser.getEmail());
    }
}
