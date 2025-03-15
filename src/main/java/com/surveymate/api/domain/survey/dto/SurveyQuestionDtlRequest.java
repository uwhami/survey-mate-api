package com.surveymate.api.domain.survey.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class SurveyQuestionDtlRequest {

    @NotNull(message = "Type ID cannot be null")
    private String typeId; // 질문 유형 ID

    @NotNull(message = "Question text cannot be null")
    private String questionText; // 질문 내용

    @NotNull(message = "Question order cannot be null")
    private Integer questionDtlOrder; // 질문 순서

    private List<SurveyQuestionSdtlRequest> options; // 선택지 목록
}