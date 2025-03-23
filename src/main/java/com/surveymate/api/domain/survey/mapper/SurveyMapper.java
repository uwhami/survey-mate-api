package com.surveymate.api.domain.survey.mapper;

import com.surveymate.api.domain.survey.dto.*;
import com.surveymate.api.domain.survey.entity.SurveyQuestionDtl;
import com.surveymate.api.domain.survey.entity.SurveyQuestionMst;
import com.surveymate.api.domain.survey.entity.SurveyQuestionSdtl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SurveyMapper {

    // SurveyQuestionMstRequest DTO -> SurveyQuestionMst 엔티티 변환
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "createMemNum", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "updateMemNum", ignore = true)
    @Mapping(target = "questions", ignore = true)
    SurveyQuestionMst toSurveyQuestionMst(SurveyQuestionMstRequest request);

    // SurveyQuestionMst 엔티티 -> SurveyQuestionMstResponse DTO 변환
    SurveyQuestionMstResponse toSurveyQuestionMstResponse(SurveyQuestionMst entity);

    // SurveyQuestionDtlRequest DTO -> SurveyQuestionDtl 엔티티 변환
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "surveyQuestionMst", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "createMemNum", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "updateMemNum", ignore = true)
    @Mapping(target = "options", ignore = true)
    SurveyQuestionDtl toSurveyQuestionDtl(SurveyQuestionDtlRequest request);

    // SurveyQuestionDtl 엔티티 -> SurveyQuestionDtlResponse DTO 변환
    @Mapping(source = "id.questionDtlOrder", target = "questionDtlOrder")
    SurveyQuestionDtlResponse toSurveyQuestionDtlResponse(SurveyQuestionDtl entity);

    // SurveyQuestionSdtlRequest DTO -> SurveyQuestionSdtl 엔티티 변환
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "surveyQuestionDtl", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "createMemNum", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "updateMemNum", ignore = true)
    SurveyQuestionSdtl toSurveyQuestionSdtl(SurveyQuestionSdtlRequest request);

    // SurveyQuestionSdtl 엔티티 -> SurveyQuestionSdtlResponse DTO 변환
    @Mapping(source = "id.questionSdtlOrder", target = "questionSdtlOrder")
    SurveyQuestionSdtlResponse toSurveyQuestionSdtlResponse(SurveyQuestionSdtl entity);
}
