package com.surveymate.api.domain.survey.service;

import com.surveymate.api.common.dto.PagedResponse;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;
import com.surveymate.api.domain.survey.dto.SurveyResponseDto;
import org.springframework.data.domain.Pageable;


public interface SurveyResponseService {

    SurveyQuestionMstResponse getSurveyForm(String surveyUrl, SurveyResponseDto responseDto, Long srMstId);

    void saveResponseData(SurveyResponseDto responseDto);

    boolean checkAlreadyResponded(String surveyUrl, String memNum);

    PagedResponse<SurveyResponseDto> getSurveyResponeList(Long groupId, String memNum, Pageable pageable);
}
