package com.surveymate.api.domain.survey.entity;

import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "survey_question_sdtl")
@Getter
@NoArgsConstructor
public class SurveyQuestionSdtl extends BaseEntity {

    @EmbeddedId
    private SurveyQuestionSdtlId id; // 복합 키

    @Column(nullable = false)
    private String optionText; // 선택지 내용

    public SurveyQuestionSdtl(SurveyQuestionSdtlId id, String optionText) {
        this.id = id;
        this.optionText = optionText;
    }
}