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
    USER_NOT_FOUND("User not found!"),
    STUDENT_FOUND_SUCCESS("Student found!"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    STUDENT_MISSING_DATA("Missing data"),
    SCHOOL_MISSING_WEBSITE("Missing school's website"),
    SCHOOL_INVALID_URL("Invalid school website's url");

    private final String text;
}

