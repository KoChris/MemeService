package com.hacking.MemeService.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(
        final ServletRequestBindingException ex,
        final HttpHeaders headers, 
        final HttpStatus status, 
        final WebRequest request) {

        String errorMessage = ex.getLocalizedMessage();

        // Note: instanceof is a code smell
        if (ex instanceof MissingRequestHeaderException) {
            MissingRequestHeaderException headerException = (MissingRequestHeaderException) ex;
            errorMessage = "Required header \'" + headerException.getHeaderName() + "\' is missing";
        }

        return new ErrorEntity(errorMessage);
    }

    @ExceptionHandler(WrongAnswerException.class)
    protected ResponseEntity<Object> handleWrongAnswer(
        final WrongAnswerException exception) {
            
            return new ErrorEntity("Answer does not match");
    }

    private class ErrorEntity extends ResponseEntity<Object> {

        public ErrorEntity(final String message) {
            super(message, HttpStatus.BAD_REQUEST);
        }

    }
}

