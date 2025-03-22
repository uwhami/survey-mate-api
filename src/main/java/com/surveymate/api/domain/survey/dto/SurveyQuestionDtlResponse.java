package com.surveymate.api.domain.survey.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionDtlResponse {

    private Integer questionDtlOrder; // 질문 순서
    private String questionText; // 질문 내용
    private String typeId; // 질문 유형 ID
    private List<SurveyQuestionSdtlResponse> options; // 선택지 목록

}
