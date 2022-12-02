package com.kowalczyk.studentclasses.configuration.securitybuilders;

import com.kowalczyk.studentclasses.enums.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * This class builds http security for developer environment.
 */
public class DevHttpSecurityBuilder implements HttpSecurityBuilder {
    @Override
    public HttpSecurity configureSecurityStandard(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .httpBasic()
                .and();
    }

    @Override
    public HttpSecurity configureSessionManagement(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
    }

    @Override
    public HttpSecurity configureAuthorization(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,".address").hasRole(Role.STUDENT.name())
                .antMatchers(HttpMethod.POST, "/student").anonymous()
                .antMatchers(HttpMethod.GET, "/student").hasAnyRole(Role.ADMIN.name(), Role.TEACHER.name())
                .antMatchers(HttpMethod.GET, "/student/*").authenticated()
                .antMatchers(HttpMethod.PATCH, "/student/*").hasRole(Role.TEACHER.name())
                .antMatchers("/student/*").hasRole(Role.STUDENT.name())
                .antMatchers("/h2-console", "/h2-console/*").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated()
                .and();
    }

    /**
     * This method additionally allows communication with H2 console.
     * @param httpSecurity - HttpSecurity instance
     * @return HttpSecurity instance with additional filters
     * @throws Exception when cannot configure security
     */
    @Override
    public HttpSecurity additionalConfiguration(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .headers()
                .frameOptions().disable()
                .and();
    }
}
