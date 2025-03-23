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
public class SurveyQuestionDtlId implements Serializable {

    @Column(name = "sq_mst_id")
    private String sqMstId;

    @Column(name = "question_dtl_order")
    private Integer questionDtlOrder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SurveyQuestionDtlId)) return false;
        SurveyQuestionDtlId that = (SurveyQuestionDtlId) o;
        return Objects.equals(sqMstId, that.sqMstId) &&
                Objects.equals(questionDtlOrder, that.questionDtlOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sqMstId, questionDtlOrder);
    }
}

