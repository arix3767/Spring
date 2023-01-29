package com.kowalczyk.studentclasses.configuration.securitybuilders;

import com.kowalczyk.studentclasses.enums.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * This class builds http security for developer environment.
 */
public class DevHttpSecurityBuilder extends BasicHttpSecurityBuilder {
    @Override
    protected ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry additionalMatchers(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        return registry.antMatchers("/h2-console", "/h2-console/*").hasRole(Role.ADMIN.name());
    }
}
