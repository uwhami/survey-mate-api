package com.surveymate.api.domain.survey.repository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.surveymate.api.domain.survey.dto.SurveyQuestionMstRequest;
import com.surveymate.api.domain.survey.entity.QSurveyQuestionMst;
import com.surveymate.api.domain.survey.entity.SurveyQuestionMst;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class SurveyQuestionMstRepositoryImpl implements SurveyQuestionMstRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SurveyQuestionMst> getSurveyQuestionMstList(SurveyQuestionMstRequest request, Pageable pageable) {
        QSurveyQuestionMst m = QSurveyQuestionMst.surveyQuestionMst;

        BooleanBuilder builder = new BooleanBuilder();

        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            builder.and(m.title.containsIgnoreCase(request.getTitle()));
        }
        if (request.getStartDate() != null) {
            builder.and(m.startDate.goe(LocalDateTime.parse(request.getStartDate())));
        }
        if (request.getEndDate() != null) {
            builder.and(m.startDate.loe(LocalDateTime.parse(request.getEndDate())));
        }


        List<SurveyQuestionMst> content = queryFactory
                .selectFrom(m)
                .where(builder)
                .orderBy(m.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 (카운트)
        Long total = Optional.ofNullable(
                queryFactory.select(m.count())
                        .from(m)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }
}