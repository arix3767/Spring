package com.kowalczyk.studentclasses.configuration.securitybuilders;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@RequiredArgsConstructor
public class HttpSecurityDirector {

    private final HttpSecurityBuilder builder;

    public HttpSecurity construct(HttpSecurity httpSecurity) throws Exception {
        httpSecurity = builder.configureSecurityStandard(httpSecurity);
        httpSecurity = builder.configureSessionManagement(httpSecurity);
        httpSecurity = builder.configureAuthorization(httpSecurity);
        httpSecurity = builder.additionalConfiguration(httpSecurity);
        return httpSecurity;
    }
}
