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

    @PostMapping("/createMenu")
    public ResponseEntity<String> createMenu(@RequestBody MenuRequest menuRequest) {
        menuService.createMenu(menuRequest);
        return ResponseEntity.ok("Menu created successfully.");
    }

    @GetMapping("/getAllMenus")
    public ResponseEntity<List<MenuResponse>> getAllMenus() {
        return ResponseEntity.ok(menuService.getAllMenus());
    }

    @GetMapping("/getAllMenusByRole")
    public ResponseEntity<List<MenuResponse>> getAllMenusByRole(@RequestBody MenuRequest menuRequest) {
        MemberRole role = MemberRole.fromAuthority(menuRequest.getMemRole());
        return ResponseEntity.ok(menuService.getAllMenusByRole(role));
    }

    @GetMapping("/getMenuHierarchy")
    public ResponseEntity<List<MenuResponse>> getMenuHierarchy() {
        return ResponseEntity.ok(menuService.getMenuHierarchy());
    }


    @PutMapping("/updateMenu/{menuNo}")
    public ResponseEntity<String> updateMenu(@PathVariable String menuNo, @RequestBody MenuRequest menuRequest) {
        menuService.updateMenu(menuNo, menuRequest);
        return ResponseEntity.ok("Menu updated successfully.");
    }

    @DeleteMapping("/delete/Menu/{menuNo}")
    public ResponseEntity<String> deleteMenu(@PathVariable String menuNo) {
        menuService.deleteMenu(menuNo);
        return ResponseEntity.ok("Menu deleted successfully.");
    }

    @GetMapping("/getMenusByRole/{role}")
    public ResponseEntity<List<MenuResponse>> getMenusByRole(@PathVariable String  role) {
        return ResponseEntity.ok(menuService.getMenusByRole(role));
    }
}
