package com.nm.patientservice.exception;

public class EmailAlreadyExistError extends RuntimeException {
    public EmailAlreadyExistError(String message) {
        super(message);
    }
}
