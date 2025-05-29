package com.surveymate.api.domain.survey.dto;

import com.surveymate.api.common.dto.MemInfoAware;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyQuestionMstRequest{

    @NotNull(message = "Title cannot be null")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title; // 설문 제목

    private String description; // 설문 설명

    private String startDate; // 설문시작일시

    private String endDate;// 설문종료일시

    private String createMemNum;// 설문생성자

    private List<SurveyQuestionDtlRequest> questions; // 질문 목록

    private String groupId; //그룹아이디

    private String sqMstId;
}
