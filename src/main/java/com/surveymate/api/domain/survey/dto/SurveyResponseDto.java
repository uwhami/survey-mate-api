package com.surveymate.api.domain.survey.dto;


import com.surveymate.api.common.dto.MemInfoAware;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class SurveyResponseDto extends MemInfoAware {

    private String sqMstId;
    private String surveyUrl;
    private List<SurveyResponseDtlDto> surveyResponse;

}
