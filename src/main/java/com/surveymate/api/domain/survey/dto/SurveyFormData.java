package com.surveymate.api.domain.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@AllArgsConstructor
public class SurveyFormData {

    private String sqMstId;
    private String title;
    private String description;
    private Long groupId;
    private Integer questionDtlOrder;
    private String typeId;
    private String questionText;
    private Integer questionSdtlOrder;
    private String optionText;

}
