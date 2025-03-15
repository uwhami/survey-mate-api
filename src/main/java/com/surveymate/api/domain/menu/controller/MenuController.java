package com.surveymate.api.domain.menu.controller;

import com.surveymate.api.common.enums.MemberRole;
import com.surveymate.api.domain.menu.dto.MenuRequest;
import com.surveymate.api.domain.menu.dto.MenuResponse;
import com.surveymate.api.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    //메뉴 레이아웃 조회
    @GetMapping("/getMenusByRole/{role}")
    public ResponseEntity<List<MenuResponse>> getMenusByRole(@PathVariable String  role) {
        return ResponseEntity.ok(menuService.getMenusByRole(role));
    }
    //메뉴생성
    @PostMapping("/createMenu")
    public ResponseEntity<String> createMenu(@RequestBody MenuRequest menuRequest) {
        menuService.createMenu(menuRequest);
        return ResponseEntity.ok("Menu created successfully.");
    }
    //모든 메뉴 조회
    @GetMapping("/getAllMenus")
    public ResponseEntity<List<MenuResponse>> getAllMenus() {
        return ResponseEntity.ok(menuService.getAllMenus());
    }


    @PutMapping("/update/{menuNo}")
    public ResponseEntity<String> updateMenu(@PathVariable String menuNo, @RequestBody MenuRequest menuRequest) {
        menuService.updateMenu(menuNo, menuRequest);
        return ResponseEntity.ok("Menu updated successfully.");
    }

    @DeleteMapping("/delete/Menu/{menuNo}")
    public ResponseEntity<String> deleteMenu(@PathVariable String menuNo) {
        menuService.deleteMenu(menuNo);
        return ResponseEntity.ok("Menu deleted successfully.");
    }


}
