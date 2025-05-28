package com.surveymate.api.domain.survey.repository;

import com.surveymate.api.domain.survey.dto.ResponsesBySurveyDto;
import com.surveymate.api.domain.survey.entity.SurveyResponseMst;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SurveyResponseMstRepository extends JpaRepository<SurveyResponseMst, Long> {

//    @Query("SELECT srm FROM SurveyResponseMst srm WHERE srm.master.sqMstId = :surveyUrl AND srm.responseMemNum = :memnum")
    SurveyResponseMst findByMaster_SqMstIdAndCreateMemNum(String sqMstId, String createMemNum);

    @Query(" SELECT new com.surveymate.api.domain.survey.dto.ResponsesBySurveyDto(" +
            "       srm.srMstId" +
            "      ,srm.createDate" +
            "      ,mem.userId" +
            "      ,mem.userName " +
            ")" +
            " FROM SurveyResponseMst srm" +
            " LEFT JOIN srm.responseMember mem" +
            " WHERE srm.master.sqMstId = :sqMstId")
    Page<ResponsesBySurveyDto> findSurveyResponseMstByMaster_SqMstId(@Param("sqMstId") String sqMstId, Pageable pageable);

}
