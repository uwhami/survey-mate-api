package com.surveymate.api.domain.survey.controller;

import com.surveymate.api.common.dto.PagedResponse;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstRequest;
import com.surveymate.api.domain.survey.dto.SurveyResponseDto;
import com.surveymate.api.domain.survey.entity.SurveyQuestionMst;
import com.surveymate.api.domain.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyService surveyService;

    /**
    * 설문생성
    * */
    @PostMapping
    public void createSurvey(@RequestBody SurveyQuestionMstRequest surveyRequest) {
        surveyService.createSurvey(surveyRequest);
    }

    /**
     * 생성 설문 리스트 조회
     * */
    @GetMapping
    public List<SurveyQuestionMst> getCreatedSurveyList(@RequestBody SurveyQuestionMstRequest surveyRequest) {
        return surveyService.getCreatedSurveyList(surveyRequest);
    }

    /**
     * 해당 설문지에 응답한 목록
     * @param sqMstId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/{sqMstId}/responses")
    public PagedResponse<SurveyResponseDto> getSurveyResponsesBySurveyId(
            @PathVariable String sqMstId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return null;
    }

}
