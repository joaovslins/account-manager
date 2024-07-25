package com.manager.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class AdviceExceptionController  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomValidatorException.class)
    public ResponseEntity<Object> customValidatorException(CustomValidatorException ex) {

        var error = ex.getMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
