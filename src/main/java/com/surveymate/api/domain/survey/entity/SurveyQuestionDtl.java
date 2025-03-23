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
    private SurveyQuestionDtlId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sq_mst_id", insertable = false, updatable = false)
    private SurveyQuestionMst surveyQuestionMst;

    @Column(name = "type_id", nullable = false)
    private String typeId;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @OneToMany(mappedBy = "surveyQuestionDtl", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyQuestionSdtl> options = new ArrayList<>();

    public SurveyQuestionDtl(SurveyQuestionMst mst, int order, String typeId, String questionText) {
        this.id = new SurveyQuestionDtlId(mst.getSqMstId(), order);
        this.surveyQuestionMst = mst;
        this.typeId = typeId;
        this.questionText = questionText;
    }
}
