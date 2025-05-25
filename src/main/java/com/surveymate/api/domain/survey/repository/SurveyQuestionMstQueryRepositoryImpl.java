package com.surveymate.api.domain.survey.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.surveymate.api.domain.survey.dto.QSurveyFormData;
import com.surveymate.api.domain.survey.dto.SurveyFormData;
import com.surveymate.api.domain.survey.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SurveyQuestionMstQueryRepositoryImpl implements SurveyQuestionMstQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SurveyFormData> getSurveyWithDetails(String surveyUrl, String memNum, Long srMstId) {
        QSurveyQuestionMst m = QSurveyQuestionMst.surveyQuestionMst;
        QSurveyQuestionDtl d = QSurveyQuestionDtl.surveyQuestionDtl;
        QSurveyQuestionSdtl s = QSurveyQuestionSdtl.surveyQuestionSdtl;
        QSurveyResponseMst rm = QSurveyResponseMst.surveyResponseMst;
        QSurveyResponseDtl dm = QSurveyResponseDtl.surveyResponseDtl;

        // SELECT 절에 사용될 표현식 초기화
        Expression<String> responseValueExpr;
        Expression<String> createMemNumExpr;

        // 기본 쿼리 구성 (select 절 제외)
        JPAQuery<?> queryBase = queryFactory
                .from(m)
                .join(m.questions, d)   //질문
                .leftJoin(d.options, s); //옵션

        // srMstId 값에 따라 조인 및 select 표현식 설정
        if (StringUtils.hasText(memNum)) {
            queryBase.leftJoin(m.responseMst, rm).on(rm.createMemNum.eq(memNum))
                    .leftJoin(rm.responseDetails, dm).on(dm.questionDtlOrder.eq(d.id.questionDtlOrder));

            responseValueExpr = dm.responseValue;
            createMemNumExpr = rm.createMemNum;
        }else if (srMstId != null) {
            queryBase.leftJoin(m.responseMst, rm).on(rm.srMstId.eq(srMstId))
                     .leftJoin(rm.responseDetails, dm).on(dm.questionDtlOrder.eq(d.id.questionDtlOrder))
                     .where(rm.srMstId.eq(srMstId));

            responseValueExpr = dm.responseValue;
            createMemNumExpr = rm.createMemNum;
        } else {
            responseValueExpr = Expressions.nullExpression(String.class);
            createMemNumExpr = Expressions.nullExpression(String.class);
        }

        if (StringUtils.hasText(surveyUrl)) {
            queryBase.where(m.url.eq(surveyUrl));
        }

        // 최종 SELECT 구성 및 쿼리 실행
        return queryBase
                .select(new QSurveyFormData(
                        m.sqMstId,
                        m.title,
                        m.description,
                        m.groupId,
                        d.id.questionDtlOrder,
                        d.typeId,
                        d.questionText,
                        s.id.questionSdtlOrder, // s가 left join 되었으므로 s.id가 null일 수 있음
                        s.optionText,          // s가 left join 되었으므로 s.optionText가 null일 수 있음
                        responseValueExpr,
                        createMemNumExpr
                ))
                .orderBy(d.id.questionDtlOrder.asc(), s.id.questionSdtlOrder.asc())
                .fetch();
    }
}


