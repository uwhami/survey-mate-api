package com.surveymate.api.domain.survey.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@ToString
public class SurveyFormData {

    private final String sqMstId;
    private final String title;
    private final String description;
    private final Long groupId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Integer questionDtlOrder;
    private final String typeId;
    private final String questionText;
    @Nullable
    private final Integer questionSdtlOrder;
    @Nullable
    private final String optionText;
    @Nullable
    private final String respondedValue;
    @Nullable
    private final String respondedMemNum;

    @QueryProjection
    public SurveyFormData(String sqMstId, String title, String description,
                          Long groupId, LocalDateTime startDate, LocalDateTime endDate,
                          Integer questionDtlOrder, String typeId,
                          String questionText, Integer questionSdtlOrder, String optionText,
                          String respondedValue, String respondedMemNum) {
        this.sqMstId = sqMstId;
        this.title = title;
        this.description = description;
        this.groupId = groupId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.questionDtlOrder = questionDtlOrder;
        this.typeId = typeId;
        this.questionText = questionText;
        this.questionSdtlOrder = questionSdtlOrder;
        this.optionText = optionText;
        this.respondedValue = respondedValue;
        this.respondedMemNum = respondedMemNum;
    }
}
