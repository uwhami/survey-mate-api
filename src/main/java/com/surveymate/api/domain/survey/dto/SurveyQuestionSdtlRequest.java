package com.surveymate.api.domain.survey.dto;

import com.surveymate.api.domain.survey.entity.SurveyQuestionDtl;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionSdtlRequest {

    @NotNull(message = "Option text cannot be null")
    private String optionText; // 선택지 내용

    @NotNull(message = "Option order cannot be null")
    private Integer questionSdtlOrder; // 선택지 순서

    @OneToMany(mappedBy = "surveyDtl", cascade = CascadeType.ALL)
    private List<SurveyQuestionDtl> questions;  // 설문에 포함된 질문들
}