package com.surveymate.api.domain.survey.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SurveyQuestionDtlId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sqMstId", nullable = false)
    private SurveyQuestionMst master; // 설문조사 마스터 ID

    private Integer questionDtlOrder; // 질문 순서 (복합 키)

    public SurveyQuestionDtlId() {}

    public SurveyQuestionDtlId(SurveyQuestionMst master, Integer questionDtlOrder) {
        this.master = master;
        this.questionDtlOrder = questionDtlOrder;
    }

    // equals & hashCode 오버라이드 (필수)
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
