package com.surveymate.api.common.exception;


import org.springframework.http.HttpStatus;

public class CustomRuntimeException extends RuntimeException{

    private final HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    public CustomRuntimeException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CustomRuntimeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public CustomRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CustomRuntimeException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
