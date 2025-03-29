package com.surveymate.api.domain.survey.dto;


import com.surveymate.api.common.dto.MemnumAware;
import lombok.Getter;

import java.util.List;


@Getter
public class SurveyResponseDto extends MemnumAware {

    private String sqMstId;
    private List<SurveyResponseDtlDto> surveyResponse;

}
