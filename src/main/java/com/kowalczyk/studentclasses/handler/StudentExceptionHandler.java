package com.kowalczyk.studentclasses.handler;

import com.kowalczyk.studentclasses.exception.StudentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentExceptionHandler {

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<String> studentAlreadyExist(StudentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
