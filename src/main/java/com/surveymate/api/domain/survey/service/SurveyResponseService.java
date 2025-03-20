package com.surveymate.api.domain.survey.service;

import com.surveymate.api.domain.survey.dto.SurveyResponseForm;

import java.util.List;

public interface SurveyResponseService {

    List<SurveyResponseForm> getSurveyForm(String surveyUrl);
}
