package com.surveymate.api.domain.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SurveyQuestionResponse {
    private String menuNo; // 메뉴 ID
    private String parentMenuNo; // 부모 메뉴 ID
    private String menuKorName; // 메뉴 한글 이름
    private String menuEngName; // 메뉴 한글 이름
    private String menuDescription; // 메뉴 설명
    private String menuPath; // 메뉴 경로
    private int sequence; // 메뉴 순서
    private String memRole; // 사용자 권한
    private String useYn; // 사용 여부

}