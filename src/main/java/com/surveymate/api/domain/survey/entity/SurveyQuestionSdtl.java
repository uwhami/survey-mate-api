package com.surveymate.api.domain.survey.entity;

import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "survey_question_sdtl")
@Getter
@Setter
@NoArgsConstructor
public class SurveyQuestionSdtl extends BaseEntity {

    @EmbeddedId
    private SurveyQuestionSdtlId id; // 복합 키

    @Column(nullable = false)
    private String optionText; // 선택지 내용

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "sqMstId", referencedColumnName = "sqMstId", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "questionDtlOrder", referencedColumnName = "questionDtlOrder", nullable = false, insertable = false, updatable = false)
    })
    private SurveyQuestionDtl surveyQuestionDtl; // 복합 키에 맞는 외래 키 설정


    public SurveyQuestionSdtl(SurveyQuestionDtl surveyQuestionDtl, int questionSdtlOrder, String optionText) {
        this.id = new SurveyQuestionSdtlId(surveyQuestionDtl, questionSdtlOrder);
        this.optionText = optionText;
    }
}