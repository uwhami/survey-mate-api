package com.surveymate.api.domain.survey.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "survey_question_sdtl")
@Getter
@NoArgsConstructor
public class SurveyQuestionSdtl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 선택지 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_id", nullable = false)
    private SurveyQuestionDtl detail; // 질문 디테일과의 관계

    @Column(nullable = false)
    private String optionText; // 선택지 내용

    @Column(nullable = false)
    private Integer order; // 선택지 순서

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
