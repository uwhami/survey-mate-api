package com.surveymate.api.domain.survey.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class SurveyQuestionMstRequest {

    @NotNull(message = "Title cannot be null")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title; // 설문 제목

    private String description; // 설문 설명

    private List<SurveyQuestionDtlRequest> questions; // 질문 목록
}