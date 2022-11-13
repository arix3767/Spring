package com.kowalczyk.studentclasses.exception;

import com.kowalczyk.studentclasses.enums.Messages;

public class AuthorizationException extends UserException {

    public AuthorizationException() {
        super(Messages.AUTHORIZATION_FAILED.getText());
    }
}
