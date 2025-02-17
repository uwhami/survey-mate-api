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

    // Menu -> MenuResponse
    @Mapping(source = "memRole", target = "memRole", qualifiedByName = "roleToString")
    @Mapping(source = "subMenus", target = "subMenus", qualifiedByName = "mapSubMenus") // ✅ 리스트 변환 적용
    MenuResponse toDTO(Menu menu);

    // MenuRequest -> Menu (Enum 변환 강제 적용)
    @Mapping(source = "memRole", target = "memRole", qualifiedByName = "stringToRole") // ✅ 여기 수정
    Menu toEntity(MenuRequest menuRequest);

    // 하위 메뉴 리스트 변환
    @Named("mapSubMenus")
    default List<MenuResponse> mapSubMenus(List<Menu> subMenus) {
        return subMenus == null ? null : subMenus.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Enum -> String 변환 (올바르게 적용 중)
    @Named("roleToString")
    static String roleToString(MemberRole role) {
        return role.getAuthority();
    }

    // String -> Enum 변환 (올바르게 적용되도록 강제)
    @Named("stringToRole")
    static MemberRole stringToRole(String role) {
        return MemberRole.fromAuthority(role); // ✅ MapStruct가 강제로 사용하도록 수정
    }
}
