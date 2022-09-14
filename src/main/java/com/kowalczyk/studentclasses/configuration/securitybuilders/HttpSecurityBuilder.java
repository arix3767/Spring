package com.kowalczyk.studentclasses.configuration.securitybuilders;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface HttpSecurityBuilder {

    HttpSecurity configureSecurityStandard(HttpSecurity httpSecurity) throws Exception;

    HttpSecurity configureSessionManagement(HttpSecurity httpSecurity) throws Exception;

    HttpSecurity configureAuthorization(HttpSecurity httpSecurity) throws Exception;

    HttpSecurity additionalConfiguration(HttpSecurity httpSecurity)throws Exception;
}
