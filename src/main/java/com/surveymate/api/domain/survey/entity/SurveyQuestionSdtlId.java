package com.surveymate.api.domain.survey.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SurveyQuestionSdtlId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "sqMstId", referencedColumnName = "sqMstId", nullable = false),
            @JoinColumn(name = "questionDtlOrder", referencedColumnName = "questionDtlOrder", nullable = false)
    })
    private SurveyQuestionDtl sqDtl; // ⚠ SurveyQuestionDtl의 복합키를 정확하게 참조

    private Integer questionSdtlOrder; // 질문 상세 순서 (PK의 일부)

    public SurveyQuestionSdtlId() {}

    public SurveyQuestionSdtlId(SurveyQuestionDtl sqDtl, Integer questionSdtlOrder) {
        this.sqDtl = sqDtl;
        this.questionSdtlOrder = questionSdtlOrder;
    }

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