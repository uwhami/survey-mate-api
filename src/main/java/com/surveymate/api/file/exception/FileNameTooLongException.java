package com.surveymate.api.file.exception;


import com.surveymate.api.common.exception.CustomRuntimeException;

public class FileNameTooLongException extends CustomRuntimeException {

    public FileNameTooLongException(String message) {
        super(message);
    }
}
