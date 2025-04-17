package com.surveymate.api.domain.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
public class SurveyResponseListDto {

    private Long srMstId;
    private LocalDateTime createData;
    private String createMemNum;
    private String userId;
    private String userName;

}
