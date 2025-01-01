package com.surveymate.api.common.init;

import com.surveymate.api.domain.survey.entity.SurveyQuestionType;
import com.surveymate.api.domain.survey.repository.SurveyQuestionTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Profile("dev")
@Component
public class SurveyQuestionTypeDataInitializer implements CommandLineRunner {

    private final SurveyQuestionTypeRepository questionTypeRepository;

    public SurveyQuestionTypeDataInitializer(SurveyQuestionTypeRepository questionTypeRepository) {
        this.questionTypeRepository = questionTypeRepository;
    }

    @Override
    public void run(String... args) {
        // 질문 유형 초기 데이터
        List<SurveyQuestionType> questionTypes = Arrays.asList(
                new SurveyQuestionType("RADIO", "단일 선택형 질문"),
                new SurveyQuestionType("CHECKBOX", "다중 선택형 질문"),
                new SurveyQuestionType("TEXT", "단일 텍스트 입력형 질문"),
                new SurveyQuestionType("TEXTAREA", "여러 줄 텍스트 입력형 질문"),
                new SurveyQuestionType("NUMBER", "숫자 입력형 질문"),
                new SurveyQuestionType("DATE", "날짜 선택형 질문"),
                new SurveyQuestionType("TIME", "시간 선택형 질문"),
                new SurveyQuestionType("RANGE", "범위 선택형 질문 (예: 1~10)"),
                new SurveyQuestionType("DROPDOWN", "드롭다운 선택형 질문")
        );

        // 기존 데이터가 없을 경우에만 추가
        if (questionTypeRepository.count() == 0) {
            questionTypeRepository.saveAll(questionTypes);
        }
    }
}
