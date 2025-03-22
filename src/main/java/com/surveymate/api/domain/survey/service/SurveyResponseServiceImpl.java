package com.surveymate.api.domain.survey.service;

import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.domain.survey.dto.SurveyFormData;
import com.surveymate.api.domain.survey.dto.SurveyQuestionDtlResponse;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;
import com.surveymate.api.domain.survey.dto.SurveyQuestionSdtlResponse;
import com.surveymate.api.domain.survey.repository.SurveyQuestionMstRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SurveyResponseServiceImpl implements SurveyResponseService {

    private final SurveyQuestionMstRepository surveyQuestionMstRepository;

    @Override
    public SurveyQuestionMstResponse getSurveyForm(String surveyUrl) {
        SurveyQuestionMstResponse response = new SurveyQuestionMstResponse();

        try {
            List<SurveyFormData> formData = surveyQuestionMstRepository.getSurveyWithDetails(surveyUrl);
            if (formData == null || formData.isEmpty()) {
                return null;
            }
            SurveyFormData questionMst = formData.get(0);
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
        }catch(Exception e){
            throw new CustomRuntimeException("Error calling Survey Response Form", e);
        }

        return response;
    }

}
