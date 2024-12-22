package com.surveymate.api.email.exception;

public class EmailAlreadyExistsException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "이미 사용중인 Email 입니다.";

    public EmailAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
