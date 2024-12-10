package com.surveymate.api.domain.member.exception;

public class PasswordMismatchException  extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "기존 비밀번호가 일치하지 않습니다.";

    public PasswordMismatchException() {
        super(DEFAULT_MESSAGE);
    }

    public PasswordMismatchException(String message) {
        super(message);
    }
}
