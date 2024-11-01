package com.surveymate.api.file.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileNameTooLongException extends RuntimeException{

    public FileNameTooLongException(String message) {
        super(message);
    }
}
