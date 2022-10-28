package com.kowalczyk.studentclasses.handler;

import com.kowalczyk.studentclasses.exception.AddressException;
import com.kowalczyk.studentclasses.exception.AddressNotFoundException;
import com.kowalczyk.studentclasses.exception.StudentException;
import com.kowalczyk.studentclasses.exception.UserNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BusinessExceptionHandler {

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<String> handleStudentException(StudentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AddressException.class)
    public ResponseEntity<String> handleAddressException(AddressException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({AddressNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
