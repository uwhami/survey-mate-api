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
    public void dataInsert() {
        // 설문 마스터 저장
        String sqMstId = codeGenerator.generateCode("SQ01");
        SurveyQuestionMst mst = new SurveyQuestionMst();
        mst.setSqMstId(sqMstId);
        mst.setUrl("test012345");
        mst.setDescription("Test Description");
        mst.setTitle("QUESTION TEST");
        surveyQuestionMstRepository.save(mst);

        // 질문1 저장
        SurveyQuestionDtlId dtlId1 = new SurveyQuestionDtlId(sqMstId, 1);
        SurveyQuestionDtl dtl1 = new SurveyQuestionDtl();
        dtl1.setId(dtlId1);
        dtl1.setSurveyQuestionMst(mst);
        dtl1.setTypeId("SQT001");
        dtl1.setQuestionText("질문1");
        surveyQuestionDtlRepository.save(dtl1);

        // 질문2 저장
        SurveyQuestionDtlId dtlId2 = new SurveyQuestionDtlId(sqMstId, 2);
        SurveyQuestionDtl dtl2 = new SurveyQuestionDtl();
        dtl2.setId(dtlId2);
        dtl2.setSurveyQuestionMst(mst);
        dtl2.setTypeId("SQT001");
        dtl2.setQuestionText("질문2");
        surveyQuestionDtlRepository.save(dtl2);

        // 질문1의 선택지 저장
        surveyQuestionSdtlRepository.save(new SurveyQuestionSdtl(dtl1, 1, "radio1"));
        surveyQuestionSdtlRepository.save(new SurveyQuestionSdtl(dtl1, 2, "radio2"));

        // 질문2의 선택지 저장
        surveyQuestionSdtlRepository.save(new SurveyQuestionSdtl(dtl2, 1, "check1"));
        surveyQuestionSdtlRepository.save(new SurveyQuestionSdtl(dtl2, 2, "check2"));
        surveyQuestionSdtlRepository.save(new SurveyQuestionSdtl(dtl2, 3, "check3"));
    }
}


