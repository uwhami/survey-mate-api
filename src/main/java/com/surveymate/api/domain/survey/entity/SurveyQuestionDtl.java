package com.surveymate.api.domain.survey.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "survey_question_dtl")
@Getter
@NoArgsConstructor
public class SurveyQuestionDtl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 질문 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private SurveyQuestionMst master; // 설문조사 마스터와의 관계

    @Column(nullable = false)
    private String typeId; // 질문 유형 ID (FK to SurveyQuestionType)

    @Column(nullable = false)
    private String questionText; // 질문 내용

    @Column(nullable = false)
    private Integer questionDtlOrder; // 질문 순서

    @Column(name = "create_date", nullable = false, updatable = false)
    private java.time.LocalDateTime createDate;

    @Column(name = "update_date")
    private java.time.LocalDateTime updateDate;


    @PrePersist
    protected void onCreate() {
        this.createDate = java.time.LocalDateTime.now();
        this.updateDate = java.time.LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = java.time.LocalDateTime.now();
    }
}
