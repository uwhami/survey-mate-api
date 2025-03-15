package com.surveymate.api.domain.survey.entity;

import com.surveymate.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "survey_question_mst")
@Getter
@Setter
@NoArgsConstructor
public class SurveyQuestionMst extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


}
