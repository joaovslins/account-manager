package com.manager.transaction.exception;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class AdviceExceptionController  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomValidatorException.class)
    public ResponseEntity<Object> customValidatorException(CustomValidatorException ex) {

        var error = ex.getMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
