package com.surveymate.api.domain.survey.controller;

import com.surveymate.api.common.dto.PagedResponse;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;
import com.surveymate.api.domain.survey.dto.SurveyResponseDto;
import com.surveymate.api.domain.survey.service.SurveyResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;



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
    public PagedResponse<SurveyResponseDto> getSurveyResponses(
            @ModelAttribute SurveyResponseDto surveyResponse,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return surveyResponseService.getSurveyResponeList(surveyResponse.getGroupId(), surveyResponse.getMemNum(), pageable);
    }

}
