package com.surveymate.api.common.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomRuntimeException extends RuntimeException{

    private final HttpStatus status;

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
