package com.surveymate.api.domain.survey.repository;

import com.surveymate.api.domain.survey.entity.SurveyQuestionMst;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyQuestionMstRepository extends JpaRepository<SurveyQuestionMst, String> {
}
