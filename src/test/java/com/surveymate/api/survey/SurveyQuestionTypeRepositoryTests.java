package com.surveymate.api.survey;

import com.surveymate.api.domain.survey.entity.SurveyQuestionType;
import com.surveymate.api.domain.survey.repository.SurveyQuestionTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SurveyQuestionTypeRepositoryTests {

    @Autowired
    SurveyQuestionTypeRepository surveyQuestionTypeRepository;

    @Test
    public void test() {
        List<SurveyQuestionType> questionTypes = Arrays.asList(
                new SurveyQuestionType("SQT001","RADIO", "단일 선택형 질문"),
                new SurveyQuestionType("SQT002","CHECKBOX", "다중 선택형 질문"),
                new SurveyQuestionType("SQT003","TEXT", "단일 텍스트 입력형 질문"),
                new SurveyQuestionType("SQT004","TEXTAREA", "여러 줄 텍스트 입력형 질문"),
                new SurveyQuestionType("SQT005","NUMBER", "숫자 입력형 질문"),
                new SurveyQuestionType("SQT006","DATE", "날짜 선택형 질문"),
                new SurveyQuestionType("SQT007","TIME", "시간 선택형 질문"),
                new SurveyQuestionType("SQT008","RANGE", "범위 선택형 질문 (예: 1-10)")
        );
        if (surveyQuestionTypeRepository.count() == 0) {
            surveyQuestionTypeRepository.saveAll(questionTypes);
        }
    }
}
