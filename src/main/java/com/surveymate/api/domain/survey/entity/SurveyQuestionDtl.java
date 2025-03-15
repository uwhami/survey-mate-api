package com.surveymate.api.domain.survey.entity;

import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "survey_question_dtl")
@Getter
@NoArgsConstructor
public class SurveyQuestionDtl extends BaseEntity {

    @EmbeddedId
    private SurveyQuestionDtlId id; // 복합 키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeId", nullable = false)
    private SurveyQuestionType type; // 질문 유형 ID (FK to SurveyQuestionType)

    @Column(nullable = false)
    private String questionText; // 질문 내용

    public SurveyQuestionDtl(SurveyQuestionDtlId id, SurveyQuestionType type, String questionText) {
        this.id = id;
        this.type = type;
        this.questionText = questionText;
    }
}