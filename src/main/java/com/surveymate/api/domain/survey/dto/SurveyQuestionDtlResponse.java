package com.surveymate.api.domain.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<String> respondedValue; // 제출한 답안


}
