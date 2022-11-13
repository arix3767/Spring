package com.kowalczyk.studentclasses.exception;

import com.kowalczyk.studentclasses.enums.Messages;

public class UserNotFoundException extends UserException {

    public UserNotFoundException() {
        super(Messages.USER_NOT_FOUND.getText());
    }
}
