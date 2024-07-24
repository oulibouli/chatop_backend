package com.chatop.portal.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * BaseException is a custom runtime exception that can be used as a base class for other exception classes
 * in an application. It provides the ability to set an HTTP status code, an error code, and a list of error
 * messages associated with the exception.
 */
@Getter // Lombok's annotation to automatically generate getters for all fields.
public class BaseException extends RuntimeException {
    private final HttpStatus status; // HTTP status code related to the exception.
    private final String code; // Custom application-specific error code.
    private final List<String> errors; // List of error messages associated with the exception.

    // Constructor to initialize the BaseException with an HTTP status, an error code, and a list of error messages.
    // If there are error messages, the first one is set as the message for the RuntimeException.
    protected BaseException(HttpStatus status, String code, List<String> errors) {
        super(errors != null && !errors.isEmpty() ? errors.get(0) : null);
        this.status = status;
        this.code = code;
        this.errors = errors;
    }
}