package com.surveymate.api.domain.survey.entity;

import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "survey_question_dtl")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionDtl extends BaseEntity {

    @EmbeddedId
    private SurveyQuestionDtlId id; // 복합 키

    @Column(nullable = false)
    private String typeId; // 질문 유형 ID (FK to SurveyQuestionType)

    @Column(nullable = false)
    private String questionText; // 질문 내용
}