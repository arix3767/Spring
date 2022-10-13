package com.kowalczyk.studentclasses.exception;

import com.kowalczyk.studentclasses.enums.Messages;

public class AddressNotFoundException extends AddressException {
    public AddressNotFoundException() {
        super(Messages.ADDRESS_NOT_FOUND.getText());
    }
}
