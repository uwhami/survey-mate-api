package com.surveymate.api.domain.survey.controller;

import com.surveymate.api.domain.survey.dto.SurveyResponseForm;
import com.surveymate.api.domain.survey.service.SurveyResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/survey/response")
public class SurveyResponseController {

    SurveyResponseService surveyResponseService;

    @GetMapping("/{surveyUrl}")
    public List<SurveyResponseForm> getSurveyForm(@PathVariable String surveyUrl) {
        return surveyResponseService.getSurveyForm(surveyUrl);
    }

}
