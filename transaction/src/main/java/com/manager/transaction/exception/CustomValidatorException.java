package com.manager.transaction.exception;

public class CustomValidatorException extends RuntimeException {
    public CustomValidatorException(String message) {
        super(message);
    }
}