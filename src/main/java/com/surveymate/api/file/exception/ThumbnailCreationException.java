package com.surveymate.api.file.exception;


import com.surveymate.api.common.exception.CustomRuntimeException;

public class ThumbnailCreationException extends CustomRuntimeException {

    public ThumbnailCreationException(String message) {
        super(message);
    }

    public ThumbnailCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
