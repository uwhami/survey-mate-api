package com.surveymate.api.domain.survey.entity;

import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "survey_question_type")
public class SurveyQuestionType extends BaseEntity {

    @Id
    @Column(nullable = false, unique = true)
    private String typeId;

    @Column(nullable = false, unique = true)
    private String name; // 질문 유형 이름 (예: RADIO, CHECKBOX, TEXT)

    @Column(nullable = false)
    private String description; // 질문 유형 설명 (예: "단일 선택형 질문")

    public SurveyQuestionType(String typeId, String name, String description) {
        this.typeId = typeId;
        this.name = name;
        this.description = description;
    }
}
