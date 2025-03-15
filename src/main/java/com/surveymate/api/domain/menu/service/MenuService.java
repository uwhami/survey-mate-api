package com.surveymate.api.domain.menu.service;

import com.surveymate.api.common.enums.MemberRole;
import com.surveymate.api.domain.menu.dto.MenuRequest;
import com.surveymate.api.domain.menu.dto.MenuResponse;
import com.surveymate.api.domain.menu.entity.Menu;

import java.util.List;

public interface MenuService {

    void createMenu(MenuRequest menuRequest);
    List<MenuResponse> getAllMenus();
    List<MenuResponse> getMenusByRole(String memberRole);
    void updateMenu(String menuNo, MenuRequest menuRequest);
    void deleteMenu(String menuNo);

}
