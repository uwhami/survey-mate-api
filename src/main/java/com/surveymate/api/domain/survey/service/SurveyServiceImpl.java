package com.surveymate.api.domain.survey.service;

import com.surveymate.api.domain.survey.dto.SurveyQuestionDtlRequest;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstRequest;
import com.surveymate.api.domain.survey.dto.SurveyQuestionSdtlRequest;
import com.surveymate.api.domain.survey.entity.*;
import com.surveymate.api.domain.survey.mapper.SurveyMapper;
import com.surveymate.api.domain.survey.repository.SurveyQuestionDtlRepository;
import com.surveymate.api.domain.survey.repository.SurveyQuestionMstRepository;
import com.surveymate.api.domain.survey.repository.SurveyQuestionSdtlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyQuestionMstRepository mstRepository;
    private final SurveyQuestionDtlRepository dtlRepository;
    private final SurveyQuestionSdtlRepository sdtlRepository;

    private final SurveyQuestionMstRepository surveyQuestionMstRepository;
    private final SurveyQuestionDtlRepository surveyQuestionDtlRepository;
    private final SurveyQuestionSdtlRepository surveyQuestionSdtlRepository;

    private final SurveyMapper surveyMapper;

    /**
     * 설문 생성
     */
    @Transactional
    public SurveyQuestionMst createSurvey(SurveyQuestionMstRequest request) {
        // 마스터 저장
        SurveyQuestionMst surveyMst = surveyMapper.toSurveyQuestionMst(request);
        surveyQuestionMstRepository.save(surveyMst);

        // 질문 저장
        for (SurveyQuestionDtlRequest dtlRequest : request.getQuestions()) {
            SurveyQuestionDtl dtlEntity = surveyMapper.toSurveyQuestionDtl(dtlRequest);
            SurveyQuestionDtlId dtlId = new SurveyQuestionDtlId();
            dtlId.setMaster(surveyMst);
            dtlId.setQuestionDtlOrder(dtlRequest.getQuestionDtlOrder());
            // 복합키에 마스터 ID 설정
            dtlEntity.setId(dtlId);
            dtlEntity.setTypeId(dtlRequest.getTypeId());
            dtlEntity.setQuestionText(dtlRequest.getQuestionText());
            surveyQuestionDtlRepository.save(dtlEntity);

            // 선택지 저장
            if (dtlRequest.getOptions() != null) {
                for (SurveyQuestionSdtlRequest sdtlRequest : dtlRequest.getOptions()) {
                    SurveyQuestionSdtl sdtlEntity = surveyMapper.toSurveyQuestionSdtl(sdtlRequest);
                    SurveyQuestionSdtlId sdtlId = new SurveyQuestionSdtlId();
                    // SurveyQuestionDtl을 복합키에 설정
                    sdtlId.setSqDtl(dtlEntity);
                    sdtlId.setQuestionSdtlOrder(sdtlRequest.getQuestionSdtlOrder());

                    // 복합키에 마스터/질문 ID 설정
                    sdtlEntity.setId(sdtlId);
                    sdtlEntity.setOptionText(sdtlRequest.getOptionText());
                    surveyQuestionSdtlRepository.save(sdtlEntity);
                }
            }
        }

        return surveyMst;
    }


}
