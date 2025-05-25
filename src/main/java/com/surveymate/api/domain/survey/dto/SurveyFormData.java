package com.surveymate.api.domain.survey.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class SurveyFormData {

    private final String sqMstId;
    private final String title;
    private final String description;
    private final Long groupId;
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
                          Long groupId, Integer questionDtlOrder, String typeId,
                          String questionText, Integer questionSdtlOrder, String optionText,
                          String respondedValue, String respondedMemNum) {
        this.sqMstId = sqMstId;
        this.title = title;
        this.description = description;
        this.groupId = groupId;
        this.questionDtlOrder = questionDtlOrder;
        this.typeId = typeId;
        this.questionText = questionText;
        this.questionSdtlOrder = questionSdtlOrder;
        this.optionText = optionText;
        this.respondedValue = respondedValue;
        this.respondedMemNum = respondedMemNum;
    }
}
