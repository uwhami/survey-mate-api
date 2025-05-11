package com.surveymate.api.domain.survey.controller;

import com.surveymate.api.common.dto.PagedResponse;
import com.surveymate.api.domain.survey.dto.ResponsesBySurveyDto;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstRequest;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;
import com.surveymate.api.domain.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/list")
    public PagedResponse<SurveyQuestionMstResponse> getCreatedSurveyList(@RequestBody SurveyQuestionMstRequest surveyRequest
        , @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return surveyService.getCreatedSurveyList(surveyRequest,pageable);

    }

    /**
     * 해당 설문지에 응답한 목록
     */
    @GetMapping("/{sqMstId}/responses")
    public PagedResponse<ResponsesBySurveyDto> getSurveyResponsesBySurveyId(
            @PathVariable String sqMstId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return surveyService.getSurveyResponsesBySurveyId(sqMstId, pageable);
    }

}
