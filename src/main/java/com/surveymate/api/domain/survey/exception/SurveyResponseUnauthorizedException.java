package com.surveymate.api.domain.survey.exception;

import com.surveymate.api.common.exception.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class SurveyResponseUnauthorizedException extends CustomRuntimeException {

    private static final String DEFAULT_MESSAGE = "SURVEY_RESPONSE_UNAUTHORIZED";

    public SurveyResponseUnauthorizedException() {
        super(DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);
    }

    public SurveyResponseUnauthorizedException(String message) {
        super(DEFAULT_MESSAGE + "(" + message + ")", HttpStatus.NOT_FOUND);
    }

}
