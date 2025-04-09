package com.surveymate.api.survey;

import com.surveymate.api.domain.survey.dto.SurveyFormData;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;
import com.surveymate.api.domain.survey.dto.SurveyResponseDto;
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
    public void getSurveyFormTest() {
        String surveyUrl = "test012345";
        String memNum = "M202502090001";
        List<SurveyFormData> list = surveyQuestionMstRepository.getSurveyWithDetails(surveyUrl, memNum);
        System.out.println("getSurveyForm : list.size() = " + list.size());
        for (SurveyFormData surveyResponseForm : list) {
            System.out.println(surveyResponseForm);
        }
    }

    @Test
    public void getSurveyFormResponseTest(){
        SurveyResponseDto responseDto = new SurveyResponseDto();
        responseDto.setMemNum("M202502090001");
        responseDto.setGroupId(15L);
        String surveyUrl = "test012345";
        SurveyQuestionMstResponse surveyQuestionMstResponse = surveyResponseService.getSurveyForm(surveyUrl, responseDto);
        System.out.println(surveyQuestionMstResponse);
    }

    @Test
    public void getSurveyResponeListTest() {
        List<SurveyResponseDto> responseDto = surveyResponseService.getSurveyResponeList(15L,"M202502090001");
        System.out.println("getSurveyResponeList : responseDto.size() = " + responseDto.size());
        for (SurveyResponseDto surveyResponseDto : responseDto) {
            System.out.println(surveyResponseDto);
        }
    }


}
