package com.currencyconverter.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseApi {

    private static final String MESSAGE_SUCCESS = "Request processed successfully!";
    private static final String MESSAGE_ERROR = "Error processing request!";

    private String message;
    private int statusCode;
    private Object data;
    private List<String> errors;

    private ResponseApi(Builder builder) {
        this.message = builder.message;
        this.statusCode = builder.statusCode;
        this.data = builder.data;
        this.errors = builder.errors;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getData() {
        return data;
    }

    public List<String> getErrors() {
        return errors;
    }

    static class Builder {
        private String message;
        private int statusCode;
        private Object data;
        private List<String> errors;

        Builder message(String message) {
            this.message = message;
            return this;
        }

        Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        Builder data(Object data) {
            this.data = data;
            return this;
        }

        Builder errors(List<String> errors) {
            this.errors = errors;
            return this;
        }

        ResponseApi build() {
            return new ResponseApi(this);
        }
    }

    public static ResponseEntity ok(Object data) {
        return new ResponseEntity<>(
            new ResponseApi.Builder()
                .statusCode(HttpStatus.OK.value())
                .data(data)
                .message(MESSAGE_SUCCESS)
                .build(),
            HttpStatus.OK
        );
    }

    public static ResponseEntity ok(Object data, HttpStatus statusCode) {
        return new ResponseEntity<>(
            new ResponseApi.Builder()
                .statusCode(statusCode.value())
                .data(data)
                .message(MESSAGE_SUCCESS)
                .build(),
            statusCode
        );
    }

    public static ResponseEntity error(List<String> errors) {
        return new ResponseEntity<>(
            new Builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(MESSAGE_ERROR)
                .errors(errors)
                .build(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    public static ResponseEntity error(List<String> errors, HttpStatus statusCode) {
        return new ResponseEntity<>(
            new Builder()
                .statusCode(statusCode.value())
                .message(MESSAGE_ERROR)
                .errors(errors)
                .build(),
            statusCode
        );
    }
}
