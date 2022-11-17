package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.Utils.SecurityUtils;
import com.kowalczyk.studentclasses.repository.UserDataRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.mockito.Mockito.mockStatic;

@SpringJUnitConfig
public class AuthorizationServiceTest {

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
        // muszę miec pewnosc ze wykonalo sie findByEmail i ze nie ma bledow
    void authorizeUser() {
        //given

        SecurityUtils.mockSecurityContextHolder(securityContextHolderMockedStatic);
        //when
        //then
    }

    private void mockSecurity() {

    }

    @Test
        // musze miec pewnosc ze odbylo sie findByEmail oraz ze jest rzucony blad AuthorizationExcention
    void shouldNotAuthorizeUserIfUserDoesNotExist() {
        //give
        //when
        //then
    }

    @Test
        // musze miec pewnosc ze odbylo sie findByEmail oraz ze jest rzucony blad AuthorizationExcention
    void shouldNotAuthorizeUserIfInvalidId() {
        //given
        //when
        //then
    }
}
