package com.surveymate.api.menu;

import com.surveymate.api.domain.auth.dto.RegisterRequest;
import com.surveymate.api.domain.menu.dto.MenuRequest;
import com.surveymate.api.domain.menu.repository.MenuRepository;
import com.surveymate.api.domain.menu.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MenuServiceTests {

    @Autowired
    MenuService menuService;

    @Autowired
    MenuRepository menuRepository;

    @Test
    @Transactional
    public void testCreateMenu() {
        MenuRequest request = new MenuRequest();

        request.setParentMenuNo(null);
        request.setMenuKorName("대시보드");
        request.setMenuEngName("Dashboard");
        request.setMenuDescription("메인 화면");
        request.setMenuPath("/dashboard");
        request.setSequence(1);
        request.setMemRole("0");

        menuService.createMenu(request);
    }
}
