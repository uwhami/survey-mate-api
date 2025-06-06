package com.surveymate.api.domain.survey.service;

import com.surveymate.api.common.dto.PagedResponse;
import com.surveymate.api.domain.survey.dto.ResponsesBySurveyDto;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstRequest;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;
import com.surveymate.api.domain.survey.entity.SurveyQuestionMst;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



public interface SurveyService {

    SurveyQuestionMst createSurvey(SurveyQuestionMstRequest surveyQuestionMstRequest);
    PagedResponse<SurveyQuestionMstResponse> getCreatedSurveyList(SurveyQuestionMstRequest surveyQuestionMstRequest, Pageable pageable);

    PagedResponse<ResponsesBySurveyDto> getSurveyResponsesBySurveyId(String sqMstId, Pageable pageable);

}
