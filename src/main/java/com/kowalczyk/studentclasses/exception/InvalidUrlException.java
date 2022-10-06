package com.kowalczyk.studentclasses.exception;

import com.kowalczyk.studentclasses.enums.Messages;

public class InvalidUrlException extends StudentException {

    public InvalidUrlException() {
        super(Messages.SCHOOL_INVALID_URL.getText());
    }
}
