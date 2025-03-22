package com.surveymate.api.survey;

import com.surveymate.api.domain.survey.dto.SurveyFormData;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;
import com.surveymate.api.domain.survey.repository.SurveyQuestionMstRepository;
import com.surveymate.api.domain.survey.service.SurveyResponseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SurveyResponseServiceTests {

    @Autowired
    private SurveyResponseService surveyResponseService;

    @Autowired
    private SurveyQuestionMstRepository surveyQuestionMstRepository;

    @Test
    public void getSurveyForm() {
        String surveyUrl = "test012345";
        List<SurveyFormData> list = surveyQuestionMstRepository.getSurveyWithDetails(surveyUrl);
        System.out.println("getSurveyForm : list.size() = " + list.size());
        for (SurveyFormData surveyResponseForm : list) {
            System.out.println(surveyResponseForm);
        }
    }

    @Test
    public void getSurveyFormResponse(){
        String surveyUrl = "test012345";
        SurveyQuestionMstResponse surveyQuestionMstResponse = surveyResponseService.getSurveyForm(surveyUrl);
        System.out.println(surveyQuestionMstResponse);
    }


}
