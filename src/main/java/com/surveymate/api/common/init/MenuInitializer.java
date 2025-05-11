package com.surveymate.api.common.init;

import com.surveymate.api.common.enums.MemberRole;
import com.surveymate.api.domain.menu.entity.Menu;
import com.surveymate.api.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Profile("dev")
@RequiredArgsConstructor
@Component
public class MenuInitializer implements CommandLineRunner {

    private final MenuRepository menuRepository;

    @Override
    public void run(String... args) throws Exception {
        // 메뉴 초기 데이터
        List<Menu> menuList = Arrays.asList(
                new Menu("MN001000000000", null, "홈", "Home", "홈 메뉴", "/", MemberRole.USER, 1, "Y"),
                new Menu("MN002000000000", null, "나의 정보", "My Info", "나의 정보", "/dashboard/user/modifyUserInfo", MemberRole.USER, 2, "Y"),
                new Menu("MN003000000000", null, "설문조사", "Survey", "설문조사", null, MemberRole.USER, 3, "Y"),
                new Menu("MN001001000000", "MN003000000000", "설문조사 만들기", "Create Survey", "설문조사 만들기", "/survey/question/create", MemberRole.USER, 1, "Y"),
                new Menu("MN001002000000", "MN003000000000", "내가 만든 설문조사 목록", "View My Survey List", "설문조사 목록", "/survey/question/list", MemberRole.USER, 2, "Y"),
                new Menu("MN001003000000", "MN003000000000", "내가 응답한 목록", "View My Response List", "내 응답 목록", "/survey/response/list", MemberRole.USER, 3, "Y"),
                new Menu("MN001002000000", "MN003000000000", "작성한 설문", "Created Surveys", "작성한 설문", "/survey/question/list", MemberRole.USER, 1, "Y"),
                new Menu("MN001003000000", "MN003000000000", "설문조사 응답보기", "View Survey Response", "설문조사 응답보기", "/survey/view", MemberRole.USER, 2, "Y"),
                new Menu("MN004000000000", null, "그룹", "Group", "Group", null, MemberRole.USER, 4, "Y"),
                new Menu("MN004001000000", "MN004000000000", "그룹 정보", "Group Info", "그룹 정보", "/group/info", MemberRole.USER, 1, "Y"),
                new Menu("MN004002000000", "MN004000000000", "그룹 수정", "Group Update", "그룹 수정", "/group/edit", MemberRole.USER, 2, "N"),
                new Menu("MN004003000000", "MN004000000000", "그룹 사용자", "Group User", "그룹 사용자", "/group/users", MemberRole.USER, 3, "Y")
        );

        // 기존 데이터가 없을 경우에만 추가
        //if (menuRepository.count() == 0) {
            menuRepository.saveAll(menuList);
        //}
    }
}
