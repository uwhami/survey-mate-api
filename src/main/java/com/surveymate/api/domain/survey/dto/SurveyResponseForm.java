package com.surveymate.api.domain.survey.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class SurveyResponseForm {

    private String sqMstId;
    private String title;
    private Integer questionDtlOrder;
    private String questionText;
    private Integer questionSdtlOrder;
    private String optionText;

}
