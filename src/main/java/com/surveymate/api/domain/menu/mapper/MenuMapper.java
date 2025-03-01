package com.surveymate.api.domain.menu.mapper;

import com.surveymate.api.domain.menu.dto.MenuRequest;
import com.surveymate.api.domain.menu.dto.MenuResponse;
import com.surveymate.api.domain.menu.entity.Menu;
import com.surveymate.api.common.enums.MemberRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    // ✅ Menu → MenuResponse 변환 (Enum & 리스트 변환 포함)
    @Mapping(source = "memRole", target = "memRole", qualifiedByName = "roleToString")
    @Mapping(source = "subMenus", target = "subMenus", qualifiedByName = "mapSubMenus")
    MenuResponse toDTO(Menu menu);

    // ✅ MenuRequest → Menu 변환 (Enum 변환 포함)
    @Mapping(source = "memRole", target = "memRole", qualifiedByName = "stringToRole")
    Menu toEntity(MenuRequest menuRequest);

    // ✅ Enum → String 변환
    @Named("roleToString")
    static String roleToString(MemberRole role) {
        return role != null ? role.getAuthority() : null;
    }

    // ✅ String → Enum 변환
    @Named("stringToRole")
    static MemberRole stringToRole(String role) {
        return role != null ? MemberRole.fromAuthority(role) : null;
    }

    // ✅ List<Menu> → List<MenuResponse> 변환
    @Named("mapSubMenus")
    static List<MenuResponse> mapSubMenus(List<Menu> subMenus) {
        return subMenus == null ? null : subMenus.stream()
                .map(menu -> new MenuResponse(
                        menu.getMenuNo(),
                        menu.getParentMenuNo(),
                        menu.getMenuKorName(),
                        menu.getMenuEngName(),
                        menu.getMenuDescription(),
                        menu.getMenuPath(),
                        menu.getSequence(),
                        roleToString(menu.getMemRole()),
                        menu.getUseYn(),
                        mapSubMenus(menu.getSubMenus()) // 재귀 호출로 하위 메뉴 변환
                ))
                .collect(Collectors.toList());
    }
}