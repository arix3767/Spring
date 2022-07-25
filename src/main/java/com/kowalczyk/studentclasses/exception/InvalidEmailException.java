package com.kowalczyk.studentclasses.exception;

import com.kowalczyk.studentclasses.enums.Messages;

public class InvalidEmailException extends StudentException {

    public InvalidEmailException(){
        super(Messages.INVALID_EMAIL.getText());
    }
}
