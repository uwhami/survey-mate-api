package com.surveymate.api.domain.survey.dto;

import lombok.Getter;

import java.util.List;


@Getter
public class SurveyResponseDtlDto {

    private int questionId;
    private List<String> answer;
}
