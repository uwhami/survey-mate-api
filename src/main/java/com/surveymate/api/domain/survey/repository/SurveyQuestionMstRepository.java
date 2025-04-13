package com.surveymate.api.domain.survey.repository;
import com.surveymate.api.domain.survey.dto.SurveyFormData;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstRequest;
import com.surveymate.api.domain.survey.dto.SurveyResponseDto;
import com.surveymate.api.domain.survey.entity.SurveyQuestionMst;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyQuestionMstRepository
        extends JpaRepository<SurveyQuestionMst, String>, SurveyQuestionMstRepositoryCustom {

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
            "    LEFT JOIN d.options s" +
            "    LEFT JOIN m.responseMst rm ON rm.createMemNum = :memNum" +
            "    LEFT JOIN d.responseDetails dm" +
            "    WHERE m.url = :surveyUrl" +
            "    ORDER BY d.id.questionDtlOrder, s.id.questionSdtlOrder")
    List<SurveyFormData> getSurveyWithDetails(@Param("surveyUrl") String surveyUrl, @Param("memNum") String memNum);


    @Query("SELECT new com.surveymate.api.domain.survey.dto.SurveyResponseDto(" +
            "      m.sqMstId" +
            "    , m.title" +
            "    , m.description" +
            "    , m.url" +
            "    , CASE WHEN rm.srMstId IS NULL THEN false ELSE true END" +
            "    , NULL" +
            ")" +
            "FROM SurveyQuestionMst m " +
            "LEFT JOIN m.responseMst rm ON rm.createMemNum = :memNum " +
            "WHERE m.groupId = :groupId OR rm.srMstId IS NOT NULL " +
            "ORDER BY m.sqMstId DESC"
    )
    Page<SurveyResponseDto> getSurveyResponeList(@Param("groupId") Long groupId, @Param("memNum") String memNum, Pageable pageable);

    Page<SurveyQuestionMst> getSurveyQuestionMstList(SurveyQuestionMstRequest request,  Pageable pageable) ;
}
