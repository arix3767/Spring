package com.kowalczyk.studentclasses.Utils;

import org.mockito.MockedStatic;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityUtils {

    private static final String PASSWORD = "password";

    private SecurityUtils() {
    }

    public static void mockSecurityContextHolder(MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic,
                                                 String username) {
        mockSecurityContextHolder(securityContextHolderMockedStatic, username, Collections.emptySet());
    }

    public static void mockSecurityContextHolder(MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic,
                                                 String username, Set<GrantedAuthority> authorities) {
        SecurityContext securityContext = mock(SecurityContext.class);
        securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
        UserDetails userDetails = User.builder()
                .username(username)
                .password(PASSWORD)
                .authorities(authorities)
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, PASSWORD, authorities);
        when(securityContext.getAuthentication()).thenReturn(authentication);

    }

    public static void mockServletContextHolder(MockedStatic<RequestContextHolder> requestContextHolderMockedStatic) {
        ServletRequestAttributes servletRequestAttributes = mock(ServletRequestAttributes.class);
        requestContextHolderMockedStatic.when(RequestContextHolder::getRequestAttributes)
                .thenReturn(servletRequestAttributes);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(servletRequestAttributes.getRequest()).thenReturn(httpServletRequest);
        String header = "Authorization";
        HttpServletRequest request = servletRequestAttributes.getRequest();
        when(request.getHeader(header)).thenReturn("1234");
    }

}
