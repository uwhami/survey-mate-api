package com.surveymate.api.domain.menu.exception;


import com.surveymate.api.common.exception.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class MenuNotFoundException extends CustomRuntimeException {

    private static final String DEFAULT_MESSAGE = "메뉴가 존재하지 않습니다.";

    public MenuNotFoundException() {
        super(DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);
    }

    public MenuNotFoundException(String message) {
        super(DEFAULT_MESSAGE + "(" + message + ")", HttpStatus.NOT_FOUND);
    }

}
