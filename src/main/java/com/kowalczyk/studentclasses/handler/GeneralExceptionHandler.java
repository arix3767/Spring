package com.kowalczyk.studentclasses.handler;

import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.exception.StudentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleAllExceptions(Throwable throwable){
        return ResponseEntity.internalServerError().body(Messages.INTERNAL_SERVER_ERROR.getText());
    }
}
