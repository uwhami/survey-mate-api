package com.surveymate.api.domain.survey.service;

import com.surveymate.api.domain.menu.dto.MenuRequest;
import com.surveymate.api.domain.menu.dto.MenuResponse;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstRequest;

import java.util.List;

public interface SurveyService {

    void createSurvey(SurveyQuestionMstRequest surveyQuestionMstRequest);
    /*List<MenuResponse> getAllMenus();
    List<MenuResponse> getMenusByRole(String memberRole);
    void updateMenu(String menuNo, MenuRequest menuRequest);
    void deleteMenu(String menuNo);*/

}
