package com.surveymate.api.domain.survey.repository;

import com.surveymate.api.domain.survey.entity.SurveyQuestionMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SurveyQuestionMstRepository extends JpaRepository<SurveyQuestionMst, String> {

    @Query("SELECT NVL(MAX(CAST(SUBSTRING(s.sqMstId, 11) AS integer)), 0) FROM SurveyQuestionMst s WHERE s.sqMstId LIKE CONCAT('SQ', :today, '%') ORDER BY s.sqMstId DESC LIMIT 1")
    int findTopSqNumByToday(@Param("today") String today);

}
