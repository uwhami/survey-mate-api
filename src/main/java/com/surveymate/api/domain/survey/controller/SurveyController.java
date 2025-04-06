package com.surveymate.api.domain.survey.controller;

import com.surveymate.api.domain.survey.dto.SurveyQuestionMstRequest;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstResponse;
import com.surveymate.api.domain.survey.dto.SurveyQuestionResponse;
import com.surveymate.api.domain.survey.entity.SurveyQuestionMst;
import com.surveymate.api.domain.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyService surveyService;

    /**
    * 설문생성
    * */
    @PostMapping
    public void createSurvey(@RequestBody SurveyQuestionMstRequest surveyRequest) {
        surveyService.createSurvey(surveyRequest);
    }

    /**
     * 설문수정
     * */
    @PatchMapping
    public SurveyQuestionMst updateSurvey(@RequestBody SurveyQuestionMstRequest surveyRequest) {
        return surveyService.updateSurvey(surveyRequest);
    }
    //설문 단건 조회
    /*@GetMapping
    public ResponseEntity<List<MenuResponse>> getAllMenus() {
        return ResponseEntity.ok(menuService.getAllMenus());
    }

    //설문 리스트 조회
    @GetMapping
    public ResponseEntity<List<MenuResponse>> getAllMenus() {
        return ResponseEntity.ok(menuService.getAllMenus());
    }



    //설문 삭제
    @DeleteMapping
    public ResponseEntity<String> deleteMenu(@PathVariable String menuNo) {
        menuService.deleteMenu(menuNo);
        return ResponseEntity.ok("Menu deleted successfully.");
    }*/


}
