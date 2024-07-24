package com.chatop.portal.exception;

import org.springframework.http.HttpStatus;

import java.util.Collections;

/**
 * TokenErrorException is a custom exception class that extends the BaseException class.
 * It is thrown when there is an error related to a token, typically during JWT validation.
 */
public class TokenErrorException extends BaseException {
    // Constructor initializes the exception with a specific unauthorized status,
    // an "TOKEN_ERROR" error code, and a detailed error message.
    public TokenErrorException(String message) {
        super(HttpStatus.UNAUTHORIZED, "TOKEN_ERROR", Collections.singletonList(message));
    }
}
