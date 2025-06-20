package com.surveymate.api.survey;

import com.surveymate.api.common.dto.PagedResponse;
import com.surveymate.api.domain.survey.dto.SurveyFormData;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;
import com.surveymate.api.domain.survey.dto.SurveyResponseDto;
import com.surveymate.api.domain.survey.entity.SurveyResponseMst;
import com.surveymate.api.domain.survey.repository.SurveyQuestionMstQueryRepositoryImpl;
import com.surveymate.api.domain.survey.repository.SurveyQuestionMstRepository;
import com.surveymate.api.domain.survey.repository.SurveyResponseMstRepository;
import com.surveymate.api.domain.survey.service.SurveyResponseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class SurveyResponseServiceTests {

    @Autowired
    private SurveyResponseMstRepository responseMstRepository;

    @Autowired
    private SurveyResponseService surveyResponseService;

    @Autowired
    private SurveyQuestionMstRepository surveyQuestionMstRepository;

    @Autowired
    private SurveyQuestionMstQueryRepositoryImpl questionMstQueryRepository;

    @Test
    public void findByMaster_SqMstIdAndCreateMemNumTest(){
        SurveyResponseMst responseMst = responseMstRepository.findByMaster_SqMstIdAndCreateMemNum("SQ202503150001", "M202502090001");
        System.out.println(responseMst);
    }

    @Test
    public void getSurveyFormTest() {
        String surveyUrl = "test012345";
        String memNum = "M202502090001";
        List<SurveyFormData> list = questionMstQueryRepository.getSurveyWithDetails(surveyUrl, memNum, null);
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
        SurveyQuestionMstResponse surveyQuestionMstResponse = surveyResponseService.getSurveyForm(surveyUrl, responseDto, null);
        System.out.println(surveyQuestionMstResponse);
    }

    @Test
    public void getSurveyResponeListTest() {
        Pageable pageable = PageRequest.of(0, 10);
        LocalDateTime dateTime1 = LocalDateTime.of(2025, 6, 11, 10, 30);
        PagedResponse<SurveyResponseDto> responseDtoPagedResponse = surveyResponseService.getSurveyResponeList(15L,"M202502090001", pageable, dateTime1);
        System.out.println("responseDtoPagedResponse : responseDto = " + responseDtoPagedResponse);
    }

    @Test
    public void getSurveyFormResponseBySrMstIdTest(){
        Long srMstId = 12L;
        SurveyQuestionMstResponse surveyQuestionMstResponse = surveyResponseService.getSurveyForm(null, null, srMstId);
        System.out.println(surveyQuestionMstResponse);
    }



}
