package com.kowalczyk.studentclasses.exception;

import com.kowalczyk.studentclasses.enums.Messages;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException() {
        super(Messages.STUDENT_NOT_FOUND.getText());
    }
}
