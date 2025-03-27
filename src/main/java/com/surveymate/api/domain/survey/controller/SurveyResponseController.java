package com.surveymate.api.domain.survey.controller;

import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;
import com.surveymate.api.domain.survey.dto.SurveyResponseDto;
import com.surveymate.api.domain.survey.service.SurveyResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/survey/response")
public class SurveyResponseController {

    private final SurveyResponseService surveyResponseService;

    @GetMapping("/{surveyUrl}")
    public SurveyQuestionMstResponse getSurveyForm(@PathVariable String surveyUrl) {
        return surveyResponseService.getSurveyForm(surveyUrl);
    }

    @PostMapping("/save")
    public void saveSurveyResponse(@RequestBody SurveyResponseDto surveyResponse) {
        System.out.println(surveyResponse);
    }

}
