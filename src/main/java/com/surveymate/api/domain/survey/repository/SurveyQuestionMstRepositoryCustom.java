package com.surveymate.api.domain.survey.repository;

import com.surveymate.api.domain.survey.dto.SurveyQuestionMstRequest;
import com.surveymate.api.domain.survey.entity.SurveyQuestionMst;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SurveyQuestionMstRepositoryCustom {
    Page<SurveyQuestionMst> getSurveyQuestionMstList(SurveyQuestionMstRequest request, Pageable pageable);
}
