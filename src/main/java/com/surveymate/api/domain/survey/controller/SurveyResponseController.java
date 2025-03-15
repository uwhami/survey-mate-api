package com.surveymate.api.domain.survey.controller;

import com.surveymate.api.domain.survey.dto.SurveyQuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/survey/response")
public class SurveyResponseController {

    @GetMapping("/{surveyurl}")
    public SurveyQuestionResponse getSurveyForm(@PathVariable String url) {
        return null;
    }

}
