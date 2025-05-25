package com.surveymate.api.domain.survey.repository;

import com.surveymate.api.domain.survey.dto.SurveyFormData;

import java.util.List;

public interface SurveyQuestionMstQueryRepository {

    List<SurveyFormData> getSurveyWithDetails(String surveyUrl, String memNum, Long srMstId);

}
