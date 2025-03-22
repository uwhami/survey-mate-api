package com.surveymate.api.domain.survey.service;

import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;


public interface SurveyResponseService {

    SurveyQuestionMstResponse getSurveyForm(String surveyUrl);
}
