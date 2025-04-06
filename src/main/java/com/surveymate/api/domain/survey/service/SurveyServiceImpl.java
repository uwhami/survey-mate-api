package com.surveymate.api.domain.survey.service;

import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.common.util.CodeGenerator;
import com.surveymate.api.common.util.UrlSHA256Generator;
import com.surveymate.api.domain.survey.dto.SurveyQuestionDtlRequest;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstRequest;
import com.surveymate.api.domain.survey.dto.SurveyQuestionSdtlRequest;
import com.surveymate.api.domain.survey.entity.*;
import com.surveymate.api.domain.survey.mapper.SurveyMapper;
import com.surveymate.api.domain.survey.repository.SurveyQuestionDtlRepository;
import com.surveymate.api.domain.survey.repository.SurveyQuestionMstRepository;
import com.surveymate.api.domain.survey.repository.SurveyQuestionSdtlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyQuestionMstRepository surveyQuestionMstRepository;
    private final SurveyQuestionDtlRepository surveyQuestionDtlRepository;
    private final SurveyQuestionSdtlRepository surveyQuestionSdtlRepository;
    private final SurveyMapper surveyMapper;
    private final CodeGenerator codeGenerator;
    private final UrlSHA256Generator urlSHA256Generator;

    /**
     * 설문 생성
     */
    @Transactional
    public SurveyQuestionMst createSurvey(SurveyQuestionMstRequest request) {
        try {
            // 마스터 저장
            log.info("Saving survey master");
            SurveyQuestionMst surveyMst = surveyMapper.toSurveyQuestionMst(request);
            String sqMstId = codeGenerator.generateCode("SQ01");
            surveyMst.setSqMstId(sqMstId);
            surveyMst.setUrl(urlSHA256Generator.generateUrlSHA256(sqMstId));
            //surveyMst.setQuestions(null);
            surveyQuestionMstRepository.save(surveyMst);

            // 질문 저장
            int questionDtlOrder = 1;
            for (SurveyQuestionDtlRequest dtlRequest : request.getQuestions()) {
                SurveyQuestionDtlId dtlId = new SurveyQuestionDtlId(sqMstId, questionDtlOrder);
                dtlRequest.setQuestionDtlOrder(questionDtlOrder);
                SurveyQuestionDtl dtlEntity = surveyMapper.toSurveyQuestionDtl(dtlRequest);
                dtlEntity.setId(dtlId);
                dtlEntity.setSurveyQuestionMst(surveyMst);
                //dtlEntity.setOptions(null);
                surveyQuestionDtlRepository.save(dtlEntity);
                // 선택지 저장
                int questionSdtlOrder = 1;
                if (dtlRequest.getOptions() != null) {
                    for (SurveyQuestionSdtlRequest sdtlRequest : dtlRequest.getOptions()) {
                        sdtlRequest.setQuestionSdtlOrder(questionSdtlOrder);
                        SurveyQuestionSdtlId sdtlId = new SurveyQuestionSdtlId(
                                sqMstId,
                                questionDtlOrder,
                                sdtlRequest.getQuestionSdtlOrder()
                        );

                        SurveyQuestionSdtl sdtlEntity = surveyMapper.toSurveyQuestionSdtl(sdtlRequest);
                        sdtlEntity.setId(sdtlId);
                        sdtlEntity.setSurveyQuestionDtl(dtlEntity);
                        surveyQuestionSdtlRepository.save(sdtlEntity);
                        questionSdtlOrder++;
                    }
                }
                questionDtlOrder++;

            }
            return surveyMst;

        } catch (Exception e) {
            log.error("Failed to create survey", e);
            throw new CustomRuntimeException("Error calling Survey Response Form", e);
        }
    }
    /**
     * 설문 수정
     */
    @Transactional
    public SurveyQuestionMst updateSurvey(SurveyQuestionMstRequest request) {
        try {
            // 마스터 조회
            log.info("Saving survey master");
            SurveyQuestionMst surveyMst = surveyMapper.toSurveyQuestionMst(request);
            String sqMstId = codeGenerator.generateCode("SQ01");
            surveyMst.setSqMstId(sqMstId);
            surveyMst.setUrl(urlSHA256Generator.generateUrlSHA256(sqMstId));
            //surveyMst.setQuestions(null);
            surveyQuestionMstRepository.save(surveyMst);

            // 질문 저장
            for (SurveyQuestionDtlRequest dtlRequest : request.getQuestions()) {
                SurveyQuestionDtlId dtlId = new SurveyQuestionDtlId(sqMstId, dtlRequest.getQuestionDtlOrder());
                SurveyQuestionDtl dtlEntity = surveyMapper.toSurveyQuestionDtl(dtlRequest);
                dtlEntity.setId(dtlId);
                dtlEntity.setSurveyQuestionMst(surveyMst);
                //dtlEntity.setOptions(null);
                surveyQuestionDtlRepository.save(dtlEntity);

                // 선택지 저장
                if (dtlRequest.getOptions() != null) {
                    for (SurveyQuestionSdtlRequest sdtlRequest : dtlRequest.getOptions()) {
                        SurveyQuestionSdtlId sdtlId = new SurveyQuestionSdtlId(
                                sqMstId,
                                dtlRequest.getQuestionDtlOrder(),
                                sdtlRequest.getQuestionSdtlOrder()
                        );

                        SurveyQuestionSdtl sdtlEntity = surveyMapper.toSurveyQuestionSdtl(sdtlRequest);
                        sdtlEntity.setId(sdtlId);
                        sdtlEntity.setSurveyQuestionDtl(dtlEntity);
                        surveyQuestionSdtlRepository.save(sdtlEntity);
                    }
                }
            }
            return surveyMst;

        } catch (Exception e) {
            log.error("Failed to create survey", e);
            throw new CustomRuntimeException("Error calling Survey Response Form", e);
        }
    }
}