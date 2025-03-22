package com.surveymate.api.domain.survey.entity;

import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "survey_question_dtl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionDtl extends BaseEntity {

    @EmbeddedId
    private SurveyQuestionDtlId id; // 복합 키

    @Column(nullable = false)
    private String typeId; // 질문 유형 ID (FK to SurveyQuestionType)

    @Column(nullable = false)
    private String questionText; // 질문 내용

    @OneToMany(mappedBy = "SurveyQuestionDtl", cascade = CascadeType.ALL)
    private List<SurveyQuestionSdtl> options = new ArrayList<>();  // 설문에 포함된 질문들

    public SurveyQuestionDtl(SurveyQuestionDtlId dtlId, String sqt001, String 질문1) {
        super();
    }
}