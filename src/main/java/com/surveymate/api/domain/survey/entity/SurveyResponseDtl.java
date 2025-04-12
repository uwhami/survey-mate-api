package com.surveymate.api.domain.survey.entity;


import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "survey_response_dtl")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponseDtl extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long srDtlId; // 응답 상세 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "srMstId", nullable = false)
    private SurveyResponseMst surveyResponseMst; // 응답 마스터와의 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "sq_mst_id", referencedColumnName = "sq_mst_id", insertable = false, updatable = false),
            @JoinColumn(name = "question_dtl_order", referencedColumnName = "question_dtl_order", insertable = false, updatable = false)
    })
    private SurveyQuestionDtl surveyQuestionDtl;

    @Column(name = "sq_mst_id")
    private String sqMstId;

    @Column(name = "question_dtl_order")
    private Integer questionDtlOrder;

    @Column
    private String responseValue;


}
