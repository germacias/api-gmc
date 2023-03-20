package com.nisum.test.apigmc.exception;


import com.nisum.test.apigmc.domain.responseModel.ErrorApi;
import com.nisum.test.apigmc.domain.responseModel.ApplicationResponseApi;
import com.nisum.test.apigmc.utils.MessageUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationHandlerController {

    private final MessageSource messageSource;
    private final String codeErrorPrefix;

    public ApplicationHandlerController(MessageSource messageSource, @Value("${custom.user.api.exception.prefix}") String codeErrorPrefix) {
        this.messageSource = messageSource;
        this.codeErrorPrefix = codeErrorPrefix;
    }

    @ExceptionHandler(UserApiException.class)
    public ResponseEntity<ApplicationResponseApi<Object>> handleUserApiAction(UserApiException e) {
        return ResponseEntity.status(e.getStatus())
                .body(this.buildResponse(this.getMessage(e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApplicationResponseApi<Object>> handleAccessDenied(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(this.buildResponse(
                        this.getMessage("permiso.denegado"), e.getMessage(), e));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApplicationResponseApi<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(this.buildResponse(e));
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApplicationResponseApi<Object>> handleSignature(SignatureException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(this.buildResponse(
                        this.getMessage("bad.credentials"), e.getMessage(), e));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApplicationResponseApi<Object>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(this.buildResponse(
                        this.getMessage("error.unknown"),
                        this.getMessage( e.getMessage()), e));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApplicationResponseApi<Object>> handleConstraintViolation(ConstraintViolationException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        return ResponseEntity.status(status).body(
                this.buildResponse(this.getMessage("error.unknown"), e.getMessage(), status, e));
    }


    @ExceptionHandler(AttemptAuthenticationException.class)
    public ResponseEntity<ApplicationResponseApi<Object>> handleAttemptAuthentication(AttemptAuthenticationException e) {
        return ResponseEntity.status(e.getStatus())
                .body(this.buildResponse(
                        this.getMessage("error.unknown"),
                        this.getMessage(e.getMessage()),
                        e.getStatus(), e)
                );
    }

    private ApplicationResponseApi<Object> buildResponse(String title, String message, Throwable e) {
        ErrorApi error = new ErrorApi(title, message, e.getClass().getName());

        ApplicationResponseApi<Object> response = new ApplicationResponseApi<>();
        response.setError(error);
        response.setMessage(message);

        return response;
    }

    private ApplicationResponseApi<Object> buildResponse(String message) {
        ApplicationResponseApi<Object> response = new ApplicationResponseApi<>();
        response.setMessage(message);

        return response;
    }

    private ApplicationResponseApi<Object> buildResponse(MethodArgumentNotValidException e) {
        ApplicationResponseApi<Object> response = new ApplicationResponseApi<>();
        Map<String,String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(
                error->{
                    errorMap.put(error.getField(),error.getDefaultMessage());
                });
        response.setMessage(errorMap);

        return response;
    }

    private ApplicationResponseApi<Object> buildResponse(String title, String message, HttpStatus status, Throwable e) {
        ErrorApi error = new ErrorApi();
        error.setTitle(title);
        error.setDetail(message);
        error.setStatus(status);
        error.setType(e.getClass().getName());
        error.setCode(this.codeErrorPrefix + status.value());

        ApplicationResponseApi<Object> response = new ApplicationResponseApi<>();
        response.setError(error);
        response.setMessage(message);

        return response;
    }

    private String getMessage(String messageKey, Object... arguments){
        return MessageUtils.getMessage(this.messageSource, messageKey, arguments);
    }

}
