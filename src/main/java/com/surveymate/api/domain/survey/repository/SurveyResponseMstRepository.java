package com.surveymate.api.domain.survey.repository;

import com.surveymate.api.domain.survey.entity.SurveyResponseMst;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyResponseMstRepository extends JpaRepository<SurveyResponseMst, Long> {

//    @Query("SELECT srm FROM SurveyResponseMst srm WHERE srm.master.sqMstId = :surveyUrl AND srm.responseMemNum = :memnum")
    SurveyResponseMst findByMaster_SqMstIdAndCreateMemNum(String sqMstId, String createMemNum);
}
