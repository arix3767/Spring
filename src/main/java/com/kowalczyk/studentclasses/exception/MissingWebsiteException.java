package com.kowalczyk.studentclasses.exception;

import com.kowalczyk.studentclasses.enums.Messages;

public class MissingWebsiteException extends StudentException {

    public MissingWebsiteException() {
        super(Messages.SCHOOL_MISSING_WEBSITE.getText());
    }
}
