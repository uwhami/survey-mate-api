package com.surveymate.api.domain.survey.repository;

import com.surveymate.api.domain.survey.dto.SurveyFormData;
import com.surveymate.api.domain.survey.entity.SurveyQuestionMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyQuestionMstRepository extends JpaRepository<SurveyQuestionMst, String> {

    @Query("SELECT NVL(MAX(CAST(SUBSTRING(s.sqMstId, 11) AS integer)), 0) FROM SurveyQuestionMst s WHERE s.sqMstId LIKE CONCAT('SQ', :today, '%') ORDER BY s.sqMstId DESC LIMIT 1")
    int findTopSqNumByToday(@Param("today") String today);

    @Query(" SELECT new com.surveymate.api.domain.survey.dto.SurveyFormData(" +
            "        m.sqMstId," +
            "        m.title," +
            "        m.description," +
            "        m.groupId," +
            "        d.id.questionDtlOrder," +
            "        d.typeId," +
            "        d.questionText," +
            "        s.id.questionSdtlOrder," +
            "        s.optionText," +
            "        dm.responseValue," +
            "        rm.createMemNum" +
            "    )" +
            "    FROM SurveyQuestionMst m" +
            "    JOIN m.questions d" +
            "    JOIN d.options s" +
            "    LEFT JOIN m.responseMst rm ON rm.createMemNum = :memNum" +
            "    LEFT JOIN d.responseDetails dm" +
            "    WHERE m.url = :surveyUrl" +
            "    ORDER BY d.id.questionDtlOrder, s.id.questionSdtlOrder")
    List<SurveyFormData> getSurveyWithDetails(@Param("surveyUrl") String surveyUrl, @Param("memNum") String memNum);


}
