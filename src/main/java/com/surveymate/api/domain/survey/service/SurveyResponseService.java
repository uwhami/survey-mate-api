package com.surveymate.api.domain.survey.service;

import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;
import com.surveymate.api.domain.survey.dto.SurveyResponseDto;

import java.util.List;


public interface SurveyResponseService {

    SurveyQuestionMstResponse getSurveyForm(String surveyUrl, SurveyResponseDto responseDto);

    void saveResponseData(SurveyResponseDto responseDto);

    boolean checkAlreadyResponded(String surveyUrl, String memNum);

    List<SurveyResponseDto> getSurveyResponeList(Long groupId, String memNum);
}
