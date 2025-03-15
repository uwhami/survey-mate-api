package com.surveymate.api.domain.survey.repository;

import com.surveymate.api.domain.survey.entity.SurveyQuestionDtl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyQuestionDtlRepository extends JpaRepository<SurveyQuestionDtl, String> {
}
