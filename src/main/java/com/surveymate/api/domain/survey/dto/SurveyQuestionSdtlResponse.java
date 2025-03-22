package com.surveymate.api.domain.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionSdtlResponse {

    private Integer questionSdtlOrder; // 선택지 순서
    private String optionText; // 선택지 내용

}
