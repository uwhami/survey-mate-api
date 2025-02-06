package com.surveymate.api.domain.member.exception;


import com.surveymate.api.common.exception.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends CustomRuntimeException {

    private static final String DEFAULT_MESSAGE = "이미 사용 중인 아이디입니다.";

    public UserAlreadyExistsException() {
        super(DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }

    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
