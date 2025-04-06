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
public class SurveyQuestionMstResponse {

    private String sqMstId; // 설문 마스터 ID (PK)
    private String title; // 설문 제목
    private String description; // 설문 설명
    private String url; // URL (SHA-256)
    private long groupId; // 그룹 아이디
    private boolean hasResponded;
    private List<SurveyQuestionDtlResponse> questions; // 질문 목록

}
