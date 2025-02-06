package com.surveymate.api.domain.member.exception;


import com.surveymate.api.common.exception.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomRuntimeException {

    private static final String DEFAULT_MESSAGE = "해당 사용자가 존재하지 않습니다.";

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException(String message) {
        super(DEFAULT_MESSAGE + "(" + message + ")", HttpStatus.NOT_FOUND);
    }

}
