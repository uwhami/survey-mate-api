package com.surveymate.api.domain.survey.service;

import com.surveymate.api.domain.survey.dto.SurveyQuestionMstRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SurveyService {

    void createSurvey(SurveyQuestionMstRequest surveyQuestionMstRequest);
    /*List<MenuResponse> getAllMenus();
    List<MenuResponse> getMenusByRole(String memberRole);
    void updateMenu(String menuNo, MenuRequest menuRequest);
    void deleteMenu(String menuNo);*/

}
