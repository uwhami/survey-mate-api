package com.surveymate.api.domain.survey.exception;

import com.surveymate.api.common.exception.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class SurveyAlreadyRespondedException extends CustomRuntimeException {

    private static final String DEFAULT_MESSAGE = "SURVEY_ALREADY_RESPONDED";

    public SurveyAlreadyRespondedException() {
        super(DEFAULT_MESSAGE, HttpStatus.BAD_REQUEST);
    }

}
