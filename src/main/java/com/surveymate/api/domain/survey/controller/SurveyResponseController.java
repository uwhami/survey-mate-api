package com.surveymate.api.domain.survey.controller;

import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;
import com.surveymate.api.domain.survey.dto.SurveyResponseDto;
import com.surveymate.api.domain.survey.service.SurveyResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/survey/response")
public class SurveyResponseController {

    private final SurveyResponseService surveyResponseService;

    @GetMapping("/{surveyUrl}")
    public SurveyQuestionMstResponse getSurveyForm(@ModelAttribute SurveyResponseDto responseDto, @PathVariable String surveyUrl) {
        return surveyResponseService.getSurveyForm(surveyUrl, responseDto);
    }

    @PostMapping("/save")
    public void saveSurveyResponse(@RequestBody SurveyResponseDto surveyResponse) {
        surveyResponseService.saveResponseData(surveyResponse);
    }

    @GetMapping("/list")
    public List<SurveyResponseDto> getSurveyResponses(@RequestBody SurveyResponseDto surveyResponse) {
        return surveyResponseService.getSurveyResponeList(surveyResponse.getGroupId(), surveyResponse.getMemNum());
    }

}
