package com.surveymate.api.domain.survey.exception;

import com.surveymate.api.common.exception.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class SurveyRequestNotFoundException extends CustomRuntimeException {

    private static final String DEFAULT_MESSAGE = "SURVEY_REQUEST_NOT_FOUND";

    public SurveyRequestNotFoundException() {
        super(DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);
    }

}
