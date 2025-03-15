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
        SurveyQuestionMst mst = new SurveyQuestionMst();
        mst.setSqMstId(codeGenerator.generateCode("SQ01"));
        mst.setUrl("test012345");
        mst.setDescription("Test Description");
        mst.setTitle("QUESTION TEST");
        surveyQuestionMstRepository.save(mst);

        SurveyQuestionDtlId dtlId = new SurveyQuestionDtlId(mst, 1);
//        SurveyQuestionType type1 = new SurveyQuestionType("SQT001", "RADIO", "단일 선택형 질문");
        SurveyQuestionDtl dtl = new SurveyQuestionDtl(dtlId, "SQT001", "질문1");
        surveyQuestionDtlRepository.save(dtl);

        SurveyQuestionSdtlId sdtlId = new SurveyQuestionSdtlId(dtl, 1);
        SurveyQuestionSdtl sdtl = new SurveyQuestionSdtl(sdtlId, "optionTest");
        surveyQuestionSdtlRepository.save(sdtl);

    }

}
