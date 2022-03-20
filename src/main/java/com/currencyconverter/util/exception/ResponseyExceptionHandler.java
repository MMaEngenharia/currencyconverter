package com.currencyconverter.util.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFindException.class)
    public ResponseEntity<Object> recordNotFindException(RecordNotFindException ex, WebRequest request) {
        return new ResponseEntity<>(
            ex.getMessage(),
            new HttpHeaders(),
            HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<Object> noContentException(NoContentException ex, WebRequest request) {
        return new ResponseEntity<>(
            ex.getMessage(),
            new HttpHeaders(),
            HttpStatus.NO_CONTENT
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(
            ex.getMessage(),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NotAcceptable.class)
    public ResponseEntity<Object> notAcceptable(NotAcceptable ex, WebRequest request) {
        return new ResponseEntity<>(
            ex.getMessage(),
            new HttpHeaders(),
            HttpStatus.NOT_ACCEPTABLE
        );
    }
}
