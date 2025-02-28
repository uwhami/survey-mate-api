package com.surveymate.api.domain.menu.service;

import com.surveymate.api.common.enums.MemberRole;
import com.surveymate.api.domain.menu.dto.MenuRequest;
import com.surveymate.api.domain.menu.dto.MenuResponse;
import com.surveymate.api.domain.menu.entity.Menu;
import com.surveymate.api.domain.menu.mapper.MenuMapper;
import com.surveymate.api.domain.menu.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @Override
    @Transactional
    public void createMenu(MenuRequest menuRequest) {

        // 부모 메뉴 ID가 null이 아닌 경우, 존재 여부 확인
        if (menuRequest.getParentMenuNo() != null) {
            boolean parentMenuExists = menuRepository.existsById(menuRequest.getParentMenuNo());
            if (!parentMenuExists) {
                throw new IllegalArgumentException("부모 메뉴 ID가 존재하지 않습니다: " + menuRequest.getParentMenuNo());
            }
        }

        Menu menu = Menu.builder()
                .menuNo(generateMenuNo(menuRequest)) // 고유 메뉴 번호 생성
                .parentMenuNo(menuRequest.getParentMenuNo())
                .menuKorName(menuRequest.getMenuKorName())
                .menuEngName(menuRequest.getMenuEngName())
                .menuDescription(menuRequest.getMenuDescription())
                .menuPath(menuRequest.getMenuPath())
                .sequence(menuRequest.getSequence())
                .memRole(MemberRole.fromAuthority(menuRequest.getMemRole()))
                .useYn("Y")
                .build();
        menuRepository.save(menu);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> getAllMenus() {
        return menuRepository.findAll().stream()
                .map(menuMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> getAllMenusByRole(MemberRole role) {
        return menuRepository.findByMemRole(role).stream()
                .map(menuMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> getMenuHierarchy() {
        List<Menu> topMenus = menuRepository.findByParentMenuNoIsNullOrderBySequence();
        return topMenus.stream()
                .map(this::buildMenuHierarchy)
                .collect(Collectors.toList());
    }

    private MenuResponse buildMenuHierarchy(Menu parentMenu) {
        MenuResponse response = menuMapper.toDTO(parentMenu);
        List<Menu> childMenus = menuRepository.findByParentMenuNoOrderBySequence(parentMenu.getMenuNo());
        response.setSubMenus(childMenus.stream()
                .map(this::buildMenuHierarchy)
                .collect(Collectors.toList()));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> getMenusByRole(String memberRole) {
        return menuRepository.findByMemRole(MemberRole.fromAuthority(memberRole)).stream()
                .map(menuMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateMenu(String menuNo, MenuRequest menuRequest) {
        Optional<Menu> existingMenu = menuRepository.findById(menuNo);
        if (existingMenu.isPresent()) {
            Menu menu = existingMenu.get();
            menu.setMenuKorName(menuRequest.getMenuKorName());
            menu.setMenuEngName(menuRequest.getMenuEngName());
            menu.setMenuDescription(menuRequest.getMenuDescription());
            menu.setMenuPath(menuRequest.getMenuPath());
            menu.setSequence(menuRequest.getSequence());
            menu.setMemRole((MemberRole.fromAuthority(menuRequest.getMemRole())));
            menu.setUseYn(menuRequest.getUseYn());
            menuRepository.save(menu);
        } else {
            throw new IllegalArgumentException("Menu not found with menuNo: " + menuNo);
        }
    }

    @Override
    @Transactional
    public void deleteMenu(String menuNo) {
        Optional<Menu> existingMenu = menuRepository.findById(menuNo);
        if (existingMenu.isPresent()) {
            Menu menu = existingMenu.get();
            menu.setUseYn("N"); // 논리적 삭제
            menuRepository.save(menu);
        } else {
            throw new IllegalArgumentException("Menu not found with menuNo: " + menuNo);
        }
    }

    public String generateMenuNo(MenuRequest menuRequest) {
        String parentMenuNo = menuRequest.getParentMenuNo();

        if (parentMenuNo == null) {
            // 부모 메뉴가 없는 경우 (최상위 메뉴)
            String maxMenuNo = menuRepository.findMaxMenuNoByPrefix("MN");
            if (maxMenuNo == null) {
                // 첫 번째 최상위 메뉴 생성
                return "MN001000000000";
            }

            // 최상위 메뉴의 마지막 번호 추출
            int lastValue = Integer.parseInt(maxMenuNo.substring(2, 5)); // "MN" 다음 3자리
            int nextValue = lastValue + 1;

            // 새로운 최상위 메뉴 번호 생성
            return "MN" + String.format("%03d", nextValue) + "000000000";
        }

        // 하위 메뉴의 경우
        String maxMenuNo = menuRepository.findMaxMenuNoByParentMenuNo(parentMenuNo);

        // 마지막 0이 아닌 문자의 인덱스를 찾기
        int lastNonZeroIndex = parentMenuNo.length() - 1;
        while (lastNonZeroIndex >= 0 && parentMenuNo.charAt(lastNonZeroIndex) == '0') {
            lastNonZeroIndex--;
        }

        // 새로운 메뉴 번호 생성 위치 결정
        int newMenuNoStartIndex = switch (lastNonZeroIndex) {
            case 4 -> 5;
            case 7 -> 8;
            default -> 11;
        };

        if (maxMenuNo == null) {
            // 첫 번째 하위 메뉴 생성
            return parentMenuNo.substring(0, newMenuNoStartIndex) + "001" + "0".repeat(14 - newMenuNoStartIndex - 3);
        }

        // 마지막 하위 메뉴 번호 추출 및 증가
        int lastValue = Integer.parseInt(maxMenuNo.substring(newMenuNoStartIndex, newMenuNoStartIndex + 3));
        int nextValue = lastValue + 1;

        // 새로운 하위 메뉴 번호 생성
        return parentMenuNo.substring(0, newMenuNoStartIndex) + String.format("%03d", nextValue) + "0".repeat(14 - newMenuNoStartIndex - 3);
    }
}
