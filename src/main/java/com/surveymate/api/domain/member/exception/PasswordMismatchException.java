package com.surveymate.api.domain.member.exception;

import com.surveymate.api.common.exception.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class PasswordMismatchException  extends CustomRuntimeException {

    private static final String DEFAULT_MESSAGE = "기존 비밀번호가 일치하지 않습니다.";

    public PasswordMismatchException() {
        super(DEFAULT_MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }

    public PasswordMismatchException(String message) {
        super(message, HttpStatus.NOT_ACCEPTABLE);
    }
}
