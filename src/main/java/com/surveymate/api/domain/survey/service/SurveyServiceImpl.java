package com.surveymate.api.domain.survey.service;

import com.surveymate.api.common.dto.PagedResponse;
import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.common.util.CodeGenerator;
import com.surveymate.api.common.util.UrlSHA256Generator;
import com.surveymate.api.domain.survey.dto.*;
import com.surveymate.api.domain.survey.entity.*;
import com.surveymate.api.domain.survey.mapper.SurveyMapper;
import com.surveymate.api.domain.survey.repository.SurveyQuestionDtlRepository;
import com.surveymate.api.domain.survey.repository.SurveyQuestionMstRepository;
import com.surveymate.api.domain.survey.repository.SurveyQuestionSdtlRepository;
import com.surveymate.api.domain.survey.repository.SurveyResponseMstRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final SurveyResponseMstRepository surveyResponseMstRepository;

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
            surveyMst.setStatus("ACTIVE");
            surveyQuestionMstRepository.save(surveyMst);

            // 질문 저장
            int questionDtlOrder = 1;
            for (SurveyQuestionDtlRequest dtlRequest : request.getQuestions()) {
                SurveyQuestionDtlId dtlId = new SurveyQuestionDtlId(sqMstId, questionDtlOrder);
                dtlRequest.setQuestionDtlOrder(questionDtlOrder);
                SurveyQuestionDtl dtlEntity = surveyMapper.toSurveyQuestionDtl(dtlRequest);
                dtlEntity.setId(dtlId);
                dtlEntity.setSurveyQuestionMst(surveyMst);
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
     * 생성된 설문 리스트 조회
     */
    @Transactional
    public PagedResponse<SurveyQuestionMstResponse> getCreatedSurveyList(SurveyQuestionMstRequest request, Pageable pageable) {
        try {

            Page<SurveyQuestionMst> response = surveyQuestionMstRepository.getSurveyQuestionMstListByCreateMemNum(request.getMemNum(),pageable);
            return new PagedResponse<>(response.map(surveyMapper::toSurveyQuestionMstResponse));

        } catch (Exception e) {
            log.error("Failed to create survey", e);
            throw new CustomRuntimeException("Error calling Survey Response Form", e);
        }
    }

    @Override
    public PagedResponse<ResponsesBySurveyDto> getSurveyResponsesBySurveyId(String sqMstId, Pageable pageable) {
        try{
            Page<ResponsesBySurveyDto> response = surveyResponseMstRepository.findSurveyResponseMstByMaster_SqMstId(sqMstId, pageable);
            return new PagedResponse<>(response);
        }catch(Exception e){
            throw new CustomRuntimeException("Error calling Survey Response List By SqMstId", e);
        }
    }
}
