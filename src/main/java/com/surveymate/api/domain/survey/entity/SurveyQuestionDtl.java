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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sqDtlId; // 질문 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sqMstId", nullable = false)
    private SurveyQuestionMst master; // 설문조사 마스터와의 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeId", nullable = false)
    private SurveyQuestionType  type; // 질문 유형 ID (FK to SurveyQuestionType)

    @Column(nullable = false)
    private String questionText; // 질문 내용

    @Column(nullable = false)
    private Integer questionDtlOrder; // 질문 순서

}
