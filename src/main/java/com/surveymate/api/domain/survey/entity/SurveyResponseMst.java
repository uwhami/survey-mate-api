package com.surveymate.api.domain.survey.entity;

import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "survey_response_mst")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponseMst extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 응답 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sq_mst_id", nullable = false)
    private SurveyQuestionMst master;

    @Column(nullable = false)
    private String responseMemNum;

}
