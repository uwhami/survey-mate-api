package com.surveymate.api.domain.member.exception;


public class UserAlreadyExistsException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "이미 사용 중인 아이디입니다.";

    public UserAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
