package com.kowalczyk.studentclasses.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException() {
        super("Student not found!");
    }
}
