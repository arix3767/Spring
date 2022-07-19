package com.kowalczyk.studentclasses.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Messages {
    STUDENT_ADD_SUCCESS("Student added successfully!"),
    STUDENT_ADD_FAILED("Student already exists!"),
    STUDENT_EDIT_SUCCESS("student data changed successfully"),
    INVALID_EMAIL("invalid email"),
    STUDENT_DELETE_SUCCESS("Student removed successfully"),
    STUDENT_UPDATE_RATE_SUCCESS("Rate changed successfully"),
    STUDENT_NOT_FOUND("Student not found!");


    private final String text;
}

