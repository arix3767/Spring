package com.kowalczyk.studentclasses.Utils;

import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityUtils {

    private SecurityUtils() {}

    public static void mockSecurityContextHolder(MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic, String username) {
        SecurityContext securityContext = mock(SecurityContext.class);
        securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(authentication.getPrincipal()).thenReturn(username);
        when(authentication.isAuthenticated()).thenReturn(true);
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
