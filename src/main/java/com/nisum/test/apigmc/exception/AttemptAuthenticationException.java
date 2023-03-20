package com.nisum.test.apigmc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class AttemptAuthenticationException extends AuthenticationException {

    private HttpStatus status;

    public AttemptAuthenticationException(String msg) {
        super(msg);
    }

    public AttemptAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AttemptAuthenticationException(String msg, Throwable cause, HttpStatus status) {
        super(msg, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
