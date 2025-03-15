package com.surveymate.api.domain.survey.service;

import com.surveymate.api.domain.survey.dto.SurveyQuestionDtlRequest;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstRequest;
import com.surveymate.api.domain.survey.dto.SurveyQuestionSdtlRequest;
import com.surveymate.api.domain.survey.entity.SurveyQuestionMst;
import com.surveymate.api.domain.survey.repository.SurveyQuestionDtlRepository;
import com.surveymate.api.domain.survey.repository.SurveyQuestionMstRepository;
import com.surveymate.api.domain.survey.repository.SurveyQuestionSdtlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyQuestionMstRepository mstRepository;
    private final SurveyQuestionDtlRepository dtlRepository;
    private final SurveyQuestionSdtlRepository sdtlRepository;

    @Override
    public void createSurvey(SurveyQuestionMstRequest request) {
        // 설문 마스터 저장
        SurveyQuestionMst master = new SurveyQuestionMst();
        master.setTitle(request.getTitle());
        master.setDescription(request.getDescription());
        master = mstRepository.save(master);

        // 설문 질문 저장
        if (request.getQuestions() != null) {
            for (SurveyQuestionDtlRequest dtlRequest : request.getQuestions()) {
                SurveyQuestionDtlRequest detail = new SurveyQuestionDtlRequest();
                //detail.setMaster(master);
                detail.setTypeId(dtlRequest.getTypeId());
                detail.setQuestionText(dtlRequest.getQuestionText());
                detail.setQuestionDtlOrder(dtlRequest.getQuestionDtlOrder());
                // = dtlRepository.save(detail);

                // 질문 선택지 저장
                if (dtlRequest.getOptions() != null) {
                    for (SurveyQuestionSdtlRequest sdtlRequest : dtlRequest.getOptions()) {
                        SurveyQuestionSdtlRequest option = new SurveyQuestionSdtlRequest();
                        //option.setDetail(detail);
                        option.setOptionText(sdtlRequest.getOptionText());
                        option.setQuestionSdtlOrder(sdtlRequest.getQuestionSdtlOrder());
                        //sdtlRepository.save(option);
                    }
                }
            }
        }

    }

}
