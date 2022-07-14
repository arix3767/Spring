package com.kowalczyk.studentclasses.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Messages {
    STUDENT_ADD_SUCCESS("Student added successfully!"),
    STUDENT_ADD_FAILED("Student already exists!"),
    STUDENT_EDIT_SUCCESS("student data changed successfuly"),
    STUDENT_EDIT_FAILED("email cannot be null"),
    STUDENT_DELETE_SUCCESS("Student removed successfuly"),
    STUDENT_UPDATE_RATE_SUCCESS("Rate changed successfuly");

    private final String text;
}

