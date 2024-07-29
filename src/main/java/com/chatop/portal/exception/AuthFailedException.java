package com.chatop.portal.exception;

import org.springframework.http.HttpStatus;

import java.util.Collections;

/**
 * AuthFailedException is a custom exception class that extends BaseException. It is used to represent authentication failure errors.
 */
public class AuthFailedException extends BaseException {
    // Constructor initializes the exception with a specific unauthorized status,
    // an "AUTH_FAILED" error code, and a detailed error message.
    public AuthFailedException(String message) {
        super(HttpStatus.UNAUTHORIZED, "AUTH_FAILED", Collections.singletonList(message));
    }
}
