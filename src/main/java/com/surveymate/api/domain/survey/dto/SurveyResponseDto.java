package com.surveymate.api.domain.survey.dto;


import com.surveymate.api.common.dto.MemInfoAware;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SurveyResponseDto extends MemInfoAware {

    private String sqMstId;
    private String title;
    private String description;
    private String surveyUrl;
    private boolean completed;
    private List<SurveyResponseDtlDto> surveyResponse;
    private Long srMstId;
    private LocalDateTime endDate;
    private LocalDateTime createDate;

    @Setter
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime clientDate;

}
