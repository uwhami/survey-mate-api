package com.surveymate.api.domain.survey.exception;


import com.surveymate.api.common.exception.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class SurveyException extends CustomRuntimeException {

    private static final String DEFAULT_MESSAGE = "메뉴가 존재하지 않습니다.";

    public SurveyException() {
        super(DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);
    }

    public SurveyException(String message) {
        super(DEFAULT_MESSAGE + "(" + message + ")", HttpStatus.NOT_FOUND);
    }

}
