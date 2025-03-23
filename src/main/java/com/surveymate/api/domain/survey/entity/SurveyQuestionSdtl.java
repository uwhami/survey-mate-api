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
    private SurveyQuestionSdtlId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "sq_mst_id", referencedColumnName = "sq_mst_id", insertable = false, updatable = false),
            @JoinColumn(name = "question_dtl_order", referencedColumnName = "question_dtl_order", insertable = false, updatable = false)
    })
    private SurveyQuestionDtl surveyQuestionDtl;

    @Column(name = "option_text", nullable = false)
    private String optionText;

    public SurveyQuestionSdtl(SurveyQuestionDtl dtl, int questionSdtlOrder, String optionText) {
        this.id = new SurveyQuestionSdtlId(
                dtl.getId().getSqMstId(),
                dtl.getId().getQuestionDtlOrder(),
                questionSdtlOrder
        );
        this.surveyQuestionDtl = dtl;
        this.optionText = optionText;
    }
}
