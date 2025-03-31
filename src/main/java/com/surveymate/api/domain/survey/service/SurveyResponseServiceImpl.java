package com.surveymate.api.domain.survey.service;

import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.domain.survey.dto.*;
import com.surveymate.api.domain.survey.entity.SurveyQuestionMst;
import com.surveymate.api.domain.survey.entity.SurveyResponseDtl;
import com.surveymate.api.domain.survey.entity.SurveyResponseMst;
import com.surveymate.api.domain.survey.exception.SurveyAlreadyRespondedException;
import com.surveymate.api.domain.survey.exception.SurveyRequestNotFoundException;
import com.surveymate.api.domain.survey.exception.SurveyResponseUnauthorizedException;
import com.surveymate.api.domain.survey.repository.SurveyQuestionMstRepository;
import com.surveymate.api.domain.survey.repository.SurveyResponseDtlRepository;
import com.surveymate.api.domain.survey.repository.SurveyResponseMstRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SurveyResponseServiceImpl implements SurveyResponseService {

    private final SurveyQuestionMstRepository questionMstRepository;
    private final SurveyResponseMstRepository responseMstRepository;
    private final SurveyResponseDtlRepository responseDtlRepository;

    @Override
    public SurveyQuestionMstResponse getSurveyForm(String surveyUrl, SurveyResponseDto responseDto) {
        SurveyQuestionMstResponse response = new SurveyQuestionMstResponse();

        try {
            List<SurveyFormData> formData = questionMstRepository.getSurveyWithDetails(surveyUrl);
            if (formData == null || formData.isEmpty()) {
                throw new SurveyRequestNotFoundException();
            }
            SurveyFormData questionMst = formData.get(0);
            if (!Objects.equals(questionMst.getGroupId(), responseDto.getGroupId())) {
                throw new SurveyResponseUnauthorizedException();
            }

            if (checkAlreadyResponded(questionMst.getSqMstId(), responseDto.getMemNum())) {
                throw new SurveyAlreadyRespondedException();
            }

            response.setSqMstId(questionMst.getSqMstId());
            response.setTitle(questionMst.getTitle());
            response.setDescription(questionMst.getDescription());
            response.setQuestions(new ArrayList<>());
            Map<Integer, SurveyQuestionDtlResponse> dtlMap = new HashMap<>();
            for (SurveyFormData item : formData) {
                int dtlOrder = item.getQuestionDtlOrder();

                SurveyQuestionDtlResponse dtlResponse = dtlMap.computeIfAbsent(dtlOrder, order -> {
                    SurveyQuestionDtlResponse newDtl = new SurveyQuestionDtlResponse();
                    newDtl.setQuestionDtlOrder(order);
                    newDtl.setQuestionText(item.getQuestionText());
                    newDtl.setTypeId(item.getTypeId());
                    newDtl.setOptions(new ArrayList<>());
                    response.getQuestions().add(newDtl);
                    return newDtl;
                });

                SurveyQuestionSdtlResponse sdtlResponse = new SurveyQuestionSdtlResponse();
                sdtlResponse.setQuestionSdtlOrder(item.getQuestionSdtlOrder());
                sdtlResponse.setOptionText(item.getOptionText());

                dtlResponse.getOptions().add(sdtlResponse);
            }
        } catch (Exception e) {
            throw new CustomRuntimeException("Error calling Survey Response Form", e);
        }

        return response;
    }

    @Transactional
    @Override
    public void saveResponseData(SurveyResponseDto responseDto) {
        try {
            SurveyQuestionMst questionMst = questionMstRepository.findById(responseDto.getSqMstId()).orElseThrow();

            if (checkAlreadyResponded(questionMst.getSqMstId(), responseDto.getMemNum())) {
                throw new SurveyAlreadyRespondedException();
            }

            SurveyResponseMst responseMst = SurveyResponseMst.builder().master(questionMst)
                    .responseMemNum(responseDto.getMemNum())
                    .build();
            responseMstRepository.save(responseMst);

            List<SurveyResponseDtlDto> responseDtlList = responseDto.getSurveyResponse();
            for (SurveyResponseDtlDto responseItem : responseDtlList) {
                String responseValue = responseItem.getAnswer().size() == 1 ? responseItem.getAnswer().get(0) : String.join("|", responseItem.getAnswer());
                SurveyResponseDtl resopnsDtl = SurveyResponseDtl.builder().surveyResponseMst(responseMst)
                        .sqMstId(responseDto.getSqMstId())
                        .questionDtlOrder(responseItem.getQuestionId())
                        .responseValue(responseValue)
                        .build();
                responseDtlRepository.save(resopnsDtl);
            }

        } catch (Exception e) {
            throw new CustomRuntimeException("Error saving Survey Response Data", e);
        }
    }

    @Override
    public boolean checkAlreadyResponded(String sqMstId, String memNum) {
        SurveyResponseMst surveyResponseMst = responseMstRepository.findByMaster_SqMstIdAndResponseMemNum(sqMstId, memNum);
        return surveyResponseMst != null;
    }
}
