package com.kowalczyk.studentclasses.exception;

import com.kowalczyk.studentclasses.enums.Messages;

public class MissingDataException extends UserException {

    public MissingDataException() {
        super(Messages.STUDENT_MISSING_DATA.getText());
    }

}
