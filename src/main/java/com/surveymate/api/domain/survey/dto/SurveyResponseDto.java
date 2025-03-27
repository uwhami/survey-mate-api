package com.surveymate.api.domain.survey.dto;


import lombok.Getter;

import java.util.List;


@Getter
public class SurveyResponseDto {

    private String sqMstId;
    private List<SurveyResponse> surveyResponse;


    @Getter
    static class SurveyResponse{
        private int questionId;
        private List<String> answer;
    }
}
