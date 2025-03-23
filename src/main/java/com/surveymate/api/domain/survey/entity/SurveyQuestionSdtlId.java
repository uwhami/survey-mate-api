package com.surveymate.api.domain.survey.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionSdtlId implements Serializable {

    @Column(name = "sq_mst_id")
    private String sqMstId;

    @Column(name = "question_dtl_order")
    private Integer questionDtlOrder;

    @Column(name = "question_sdtl_order")
    private Integer questionSdtlOrder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SurveyQuestionSdtlId)) return false;
        SurveyQuestionSdtlId that = (SurveyQuestionSdtlId) o;
        return Objects.equals(sqMstId, that.sqMstId) &&
                Objects.equals(questionDtlOrder, that.questionDtlOrder) &&
                Objects.equals(questionSdtlOrder, that.questionSdtlOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sqMstId, questionDtlOrder, questionSdtlOrder);
    }
}
