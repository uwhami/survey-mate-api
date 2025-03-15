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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sqSdtlId; // 선택지 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sqDtlId", nullable = false)
    private SurveyQuestionDtl sqDtl; // 질문 디테일과의 관계

    @Column(nullable = false)
    private String optionText; // 선택지 내용

    @Column(nullable = false)
    private Integer questionSdtlOrder; // 선택지 순서

}
