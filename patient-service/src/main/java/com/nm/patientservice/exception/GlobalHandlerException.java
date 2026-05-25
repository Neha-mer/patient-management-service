package com.nm.patientservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return  ResponseEntity.badRequest().body(errors.toString());
    }

    @ExceptionHandler(EmailAlreadyExistError.class)
    public ResponseEntity<String> handleEmailAlreadyExistError(EmailAlreadyExistError emailAlreadyExistError) {

      log.error(emailAlreadyExistError.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("Email Already Exists", emailAlreadyExistError.getMessage());
        return ResponseEntity.badRequest().body(errors.toString());
    }


    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> handlePatientNotFoundException(PatientNotFoundException patientNotFoundException) {
        log.error(patientNotFoundException.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("Patient Not Found", patientNotFoundException.getMessage());
        return ResponseEntity.badRequest().body(errors.toString());
    }
}

