package com.surveymate.api.survey;

import com.surveymate.api.common.util.CodeGenerator;
import com.surveymate.api.domain.survey.entity.*;
import com.surveymate.api.domain.survey.repository.SurveyQuestionDtlRepository;
import com.surveymate.api.domain.survey.repository.SurveyQuestionMstRepository;
import com.surveymate.api.domain.survey.repository.SurveyQuestionSdtlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SurveyServiceTests {

    @Autowired
    SurveyQuestionMstRepository surveyQuestionMstRepository;

    @Autowired
    SurveyQuestionDtlRepository surveyQuestionDtlRepository;

    @Autowired
    SurveyQuestionSdtlRepository surveyQuestionSdtlRepository;

    @Autowired
    CodeGenerator codeGenerator;

    @Test
    public void dataInsert(){
        // 설문 마스터 저장
        SurveyQuestionMst mst = new SurveyQuestionMst();
        mst.setSqMstId(codeGenerator.generateCode("SQ01"));
        mst.setUrl("test012345");
        mst.setDescription("Test Description");
        mst.setTitle("QUESTION TEST");
        surveyQuestionMstRepository.save(mst);

        // 설문 질문 저장
        SurveyQuestionDtlId dtlId = new SurveyQuestionDtlId(mst, 1);
        SurveyQuestionDtlId dtlId2 = new SurveyQuestionDtlId(mst, 2);
        SurveyQuestionDtl dtl = new SurveyQuestionDtl(dtlId, "SQT001", "질문1");
        SurveyQuestionDtl dtl2 = new SurveyQuestionDtl(dtlId2, "SQT001", "질문2");
        surveyQuestionDtlRepository.save(dtl);
        surveyQuestionDtlRepository.save(dtl2);

        // 설문 선택지 저장
        SurveyQuestionSdtl sdtl = new SurveyQuestionSdtl(new SurveyQuestionSdtlId(dtl, 1), "radio1");
        SurveyQuestionSdtl sdtl2 = new SurveyQuestionSdtl(new SurveyQuestionSdtlId(dtl, 2), "radio2");
        surveyQuestionSdtlRepository.save(sdtl);
        surveyQuestionSdtlRepository.save(sdtl2);

        // 두 번째 설문 질문에 대한 선택지 저장
        SurveyQuestionSdtl sdtl2_1 = new SurveyQuestionSdtl(new SurveyQuestionSdtlId(dtl2, 1), "check1");
        SurveyQuestionSdtl sdtl2_2 = new SurveyQuestionSdtl(new SurveyQuestionSdtlId(dtl2, 2), "check2");
        SurveyQuestionSdtl sdtl2_3 = new SurveyQuestionSdtl(new SurveyQuestionSdtlId(dtl2, 3), "check3");
        surveyQuestionSdtlRepository.save(sdtl2_1);
        surveyQuestionSdtlRepository.save(sdtl2_2);
        surveyQuestionSdtlRepository.save(sdtl2_3);


    }

}
