package com.surveymate.api.file.exception;


public class FileNameTooLongException extends RuntimeException{

    public FileNameTooLongException(String message) {
        super(message);
    }
}
