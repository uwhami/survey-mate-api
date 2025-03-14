package com.surveymate.api.domain.survey.entity;


import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "survey_response_dtl")
@Getter
@NoArgsConstructor
public class SurveyResponseDtl extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 응답 상세 ID (PK)

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id", nullable = false)
    private SurveyResponseMst response; // 응답 마스터와의 관계*/

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionDtl_id", nullable = false)
    private SurveyQuestionDtl question; // 질문 디테일과의 관계*/

    @Column(nullable = true)
    private String answerText; // 주관식 답변 (null 가능)

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = true)
    private SurveyQuestionSdtl option; // 선택된 옵션 (객관식의 경우)*/

}
