package com.surveymate.api.domain.survey.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "survey_question_mst")
@Getter
@NoArgsConstructor
public class SurveyQuestionMst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 설문 ID (PK)

    @Column(nullable = false)
    private String title; // 설문 제목

    @Column(nullable = true)
    private String description; // 설문 설명

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
