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
        SurveyQuestionDtlId dtlId2 = new SurveyQuestionDtlId(mst, 2);
        SurveyQuestionDtl dtl = new SurveyQuestionDtl(dtlId, "SQT001", "질문1");
        SurveyQuestionDtl dtl2 = new SurveyQuestionDtl(dtlId2, "SQT001", "질문2");
        surveyQuestionDtlRepository.save(dtl);
        surveyQuestionDtlRepository.save(dtl2);

        SurveyQuestionSdtlId sdtlId = new SurveyQuestionSdtlId(dtl, 1);
        SurveyQuestionSdtlId sdtlId2 = new SurveyQuestionSdtlId(dtl, 2);
        SurveyQuestionSdtl sdtl = new SurveyQuestionSdtl(sdtlId, "radio1");
        SurveyQuestionSdtl sdtl2 = new SurveyQuestionSdtl(sdtlId2, "radio2");
        surveyQuestionSdtlRepository.save(sdtl);
        surveyQuestionSdtlRepository.save(sdtl2);

        SurveyQuestionSdtlId sdtlId2_1 = new SurveyQuestionSdtlId(dtl2, 1);
        SurveyQuestionSdtlId sdtlId2_2 = new SurveyQuestionSdtlId(dtl2, 2);
        SurveyQuestionSdtlId sdtlId2_3 = new SurveyQuestionSdtlId(dtl2, 2);

        SurveyQuestionSdtl sdtl2_1 = new SurveyQuestionSdtl(sdtlId2_1, "check1");
        SurveyQuestionSdtl sdtl2_2 = new SurveyQuestionSdtl(sdtlId2_2, "check2");
        SurveyQuestionSdtl sdtl2_3 = new SurveyQuestionSdtl(sdtlId2_3, "check3");
        surveyQuestionSdtlRepository.save(sdtl2_1);
        surveyQuestionSdtlRepository.save(sdtl2_2);
        surveyQuestionSdtlRepository.save(sdtl2_3);


    }

}
