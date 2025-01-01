package com.surveymate.api.domain.survey.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "survey_question_type")
public class SurveyQuestionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // 질문 유형 이름 (예: RADIO, CHECKBOX, TEXT)

    @Column(nullable = false)
    private String description; // 질문 유형 설명 (예: "단일 선택형 질문")

    public SurveyQuestionType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
