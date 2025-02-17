package com.surveymate.api.domain.menu.dto;

import com.surveymate.api.common.enums.MemberRole;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MenuResponse {
    private String menuNo; // 메뉴 ID
    private String parentMenuNo; // 부모 메뉴 ID
    private String menuKorName; // 메뉴 한글 이름
    private String menuEngName; // 메뉴 한글 이름
    private String menuDescription; // 메뉴 설명
    private String menuPath; // 메뉴 경로
    private int sequence; // 메뉴 순서
    private MemberRole memRole; // 사용자 권한
    private String useYn; // 사용 여부
    private List<MenuResponse> subMenus = new ArrayList<>(); // 자식 메뉴 리스트
}