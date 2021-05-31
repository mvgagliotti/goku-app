package com.goku.gokubackend.application.exceptionhandling;

import com.goku.gokubackend.application.auth.ForbiddenAccessException;
import com.goku.gokubackend.domain.exception.DomainException;
import com.goku.gokubackend.domain.exception.InvalidCredentialsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EndpointsExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidCredentialsException.class})
    protected ResponseEntity<Object> handleInvalidCredentials(RuntimeException ex,
                                                              WebRequest request) {
        return handleExceptionInternal(ex, "Invalid credentials", new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {DomainException.class})
    protected ResponseEntity<Object> handleDomainException(RuntimeException ex,
                                                              WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ForbiddenAccessException.class})
    protected ResponseEntity<Object> handleForbiddenException(RuntimeException ex,
                                                           WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

}
