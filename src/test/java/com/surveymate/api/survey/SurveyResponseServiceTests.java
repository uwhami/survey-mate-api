package com.surveymate.api.survey;

import com.surveymate.api.domain.survey.dto.SurveyResponseForm;
import com.surveymate.api.domain.survey.service.SurveyResponseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SurveyResponseServiceTests {

    @Autowired
    private SurveyResponseService surveyResponseService;

    @Test
    public void getSurveyForm() {
        String surveyUrl = "test012345";
        List<SurveyResponseForm> list = surveyResponseService.getSurveyForm(surveyUrl);
        System.out.println("getSurveyForm : list.size() = " + list.size());
        for (SurveyResponseForm surveyResponseForm : list) {
            System.out.println(surveyResponseForm);
        }
    }


}
