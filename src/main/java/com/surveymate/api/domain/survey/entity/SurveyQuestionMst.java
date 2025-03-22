package com.surveymate.api.domain.survey.entity;

import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String sqMstId; // 설문 마스터 ID (PK)

    @NotNull(message = "Title cannot be null")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    @Column(nullable = false)
    private String title; // 설문 제목

    @Column
    private String description; // 설문 설명 (nullable 기본값)

    @Column(nullable = false)
    private String url; //URL(SHA-256)

    @Column
    private String groupId; //그룹아이디

    @OneToMany(mappedBy = "SurveyQuestionMst", cascade = CascadeType.ALL)
    private List<SurveyQuestionDtl> questions = new ArrayList<>();  // 설문에 포함된 질문들


}
