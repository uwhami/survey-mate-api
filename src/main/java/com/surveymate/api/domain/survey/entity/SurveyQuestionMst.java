package com.surveymate.api.domain.survey.entity;

import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "survey_question_mst")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionMst extends BaseEntity {

    @Id
    @Column(name = "sq_mst_id")
    private String sqMstId;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String url;

    private Long groupId;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private boolean isAnonymous;

    @Column(nullable = false )
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "surveyQuestionMst", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyQuestionDtl> questions = new ArrayList<>();

    @OneToMany(mappedBy = "master", fetch = FetchType.LAZY)
    List<SurveyResponseMst> responseMst;
}

