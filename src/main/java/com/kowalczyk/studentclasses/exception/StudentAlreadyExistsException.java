package com.kowalczyk.studentclasses.exception;

import com.kowalczyk.studentclasses.enums.Messages;

public class StudentAlreadyExistsException extends StudentException {

    public StudentAlreadyExistsException() {
        super(Messages.STUDENT_ADD_FAILED.getText());
    }
}
