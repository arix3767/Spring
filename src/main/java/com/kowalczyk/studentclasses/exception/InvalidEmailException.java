package com.kowalczyk.studentclasses.exception;

import com.kowalczyk.studentclasses.enums.Messages;

public class InvalidEmailException extends UserException {

    public InvalidEmailException(){
        super(Messages.INVALID_EMAIL.getText());
    }
}
