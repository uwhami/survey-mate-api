package com.surveymate.api.domain.survey.service;

import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;
import com.surveymate.api.domain.survey.dto.SurveyResponseDto;


public interface SurveyResponseService {

    SurveyQuestionMstResponse getSurveyForm(String surveyUrl);

    void saveResponseData(SurveyResponseDto responseDto);
}
