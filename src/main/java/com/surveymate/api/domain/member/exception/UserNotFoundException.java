package com.surveymate.api.domain.member.exception;



public class UserNotFoundException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "해당 사용자가 존재하지 않습니다.";

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
