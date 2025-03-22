package com.surveymate.api.domain.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionSdtlRequest {

    @NotNull(message = "Option text cannot be null")
    private String optionText; // 선택지 내용

    @NotNull(message = "Option order cannot be null")
    private Integer questionSdtlOrder; // 선택지 순서
}