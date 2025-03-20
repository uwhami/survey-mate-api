package com.surveymate.api.domain.survey.service;

import com.surveymate.api.domain.survey.dto.SurveyResponseForm;
import com.surveymate.api.domain.survey.repository.SurveyQuestionMstRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SurveyResponseServiceImpl implements SurveyResponseService {

    private final SurveyQuestionMstRepository surveyQuestionMstRepository;

    @Override
    public List<SurveyResponseForm> getSurveyForm(String surveyUrl) {
        return surveyQuestionMstRepository.getSurveyWithDetails(surveyUrl);
    }

}
