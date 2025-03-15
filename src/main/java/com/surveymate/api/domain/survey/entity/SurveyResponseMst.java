package com.surveymate.api.domain.survey.entity;

import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "survey_response_mst")
@Getter
@NoArgsConstructor
public class SurveyResponseMst extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 응답 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id", nullable = false)
    private SurveyQuestionMst master; // 설문조사 마스터와의 관계

    @Column(nullable = false)
    private Long userId; // 사용자 ID

}
