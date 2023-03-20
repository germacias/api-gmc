package com.nisum.test.apigmc.exception;

import org.springframework.http.HttpStatus;

public class UserApiException extends RuntimeException{

    private HttpStatus status;

    public UserApiException(String message) {
        super(message);
    }

    public UserApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public UserApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
