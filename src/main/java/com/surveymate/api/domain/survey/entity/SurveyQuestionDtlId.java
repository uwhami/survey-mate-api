package com.surveymate.api.domain.survey.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class SurveyQuestionDtlId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sqMstId", nullable = false) // ⚠ FK 명확하게 매핑
    private SurveyQuestionMst master; // 설문조사 마스터 ID (PK의 일부)

    private Integer questionDtlOrder; // 질문 순서 (PK의 일부)

    public SurveyQuestionDtlId() {}

    public SurveyQuestionDtlId(SurveyQuestionMst master, Integer questionDtlOrder) {
        this.master = master;
        this.questionDtlOrder = questionDtlOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyQuestionDtlId that = (SurveyQuestionDtlId) o;
        return Objects.equals(master, that.master) &&
                Objects.equals(questionDtlOrder, that.questionDtlOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(master, questionDtlOrder);
    }
}