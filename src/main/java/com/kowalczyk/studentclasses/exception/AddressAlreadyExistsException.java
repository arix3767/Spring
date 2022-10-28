package com.kowalczyk.studentclasses.exception;

import com.kowalczyk.studentclasses.enums.Messages;

public class AddressAlreadyExistsException extends AddressException{
    public AddressAlreadyExistsException() {
        super(Messages.ADDRESS_ADD_FAILED.getText());
    }
}
