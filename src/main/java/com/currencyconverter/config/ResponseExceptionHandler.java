package com.currencyconverter.config;

import com.currencyconverter.util.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(ResponseExceptionHandler.class);

    private final String MESSAGE_ERROR = "Report error: ";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        log.error(MESSAGE_ERROR, ex);
        return ResponseApi.error(details);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>(ex.getConstraintViolations().size());
        getConstraintValidations(ex.getConstraintViolations(), errors);
        log.error(MESSAGE_ERROR, ex);
        return ResponseApi.error(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity badCredentialsException(BadCredentialsException ex, WebRequest request) {
        return ResponseApi.ok(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity usernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        return ResponseApi.ok(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoContentException.class)
    public ResponseEntity noContentException(NoContentException ex, WebRequest request) {
        return ResponseApi.ok(ex.getMessage(), HttpStatus.NO_CONTENT);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundException(NotFoundException ex, WebRequest request) {
        return ResponseApi.ok(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFindException.class)
    public ResponseEntity recordNotFindException(RecordNotFindException ex, WebRequest request) {
        return ResponseApi.ok(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(NotAcceptableException.class)
    public ResponseEntity notAcceptableException(NotAcceptableException ex, WebRequest request) {
        return ResponseApi.ok(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity badRequestException(BadRequestException ex, WebRequest request) {
        log.error(MESSAGE_ERROR, ex);
        return ResponseApi.ok(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity duplicateRecordException(DuplicateRecordException ex, WebRequest request) {
        return ResponseApi.ok(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private void getConstraintValidations(Set<ConstraintViolation<?>> violations, List<String> errors) {
        violations.forEach(e -> errors.add(e.getMessage()));
    }
}
