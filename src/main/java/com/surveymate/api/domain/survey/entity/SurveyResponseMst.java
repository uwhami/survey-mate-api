package com.surveymate.api.domain.survey.entity;

import com.surveymate.api.common.entity.BaseEntity;
import com.surveymate.api.domain.member.entity.Member;
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
    private Long srMstId; // 응답 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sq_mst_id", nullable = false)
    private SurveyQuestionMst master;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_mem_num", referencedColumnName = "mem_num", insertable=false, updatable=false)
    private Member responseMember;


}
