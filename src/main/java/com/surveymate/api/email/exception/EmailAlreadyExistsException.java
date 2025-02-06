package com.surveymate.api.email.exception;

import com.surveymate.api.common.exception.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends CustomRuntimeException {

    private static final String DEFAULT_MESSAGE = "이미 사용중인 Email 입니다.";

    public EmailAlreadyExistsException() {
        super(DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }

    public EmailAlreadyExistsException(String message) {
        super(DEFAULT_MESSAGE + "(" + message + ")", HttpStatus.CONFLICT);
    }
}
