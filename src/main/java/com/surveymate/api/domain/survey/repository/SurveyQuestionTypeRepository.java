package com.surveymate.api.domain.survey.repository;

import com.surveymate.api.domain.survey.entity.SurveyQuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyQuestionTypeRepository extends JpaRepository<SurveyQuestionType, String> {
}
