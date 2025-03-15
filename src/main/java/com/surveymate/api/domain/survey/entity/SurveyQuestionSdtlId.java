package com.surveymate.api.domain.survey.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SurveyQuestionSdtlId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY) // 외래 키 관계
    @JoinColumn(name = "sqDtlId", nullable = false)
    private SurveyQuestionDtl sqDtl;

    private Integer questionSdtlOrder;

    public SurveyQuestionSdtlId() {}

    public SurveyQuestionSdtlId(SurveyQuestionDtl sqDtl, Integer questionSdtlOrder) {
        this.sqDtl = sqDtl;
        this.questionSdtlOrder = questionSdtlOrder;
    }

    // equals & hashCode 오버라이드 (필수)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyQuestionSdtlId that = (SurveyQuestionSdtlId) o;
        return Objects.equals(sqDtl, that.sqDtl) &&
                Objects.equals(questionSdtlOrder, that.questionSdtlOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sqDtl, questionSdtlOrder);
    }
}