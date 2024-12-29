package com.surveymate.api.config;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SurveymateP6SpyFormatter implements MessageFormattingStrategy {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        // 현재 시간을 사람이 읽을 수 있는 형식으로 변환
        String formattedCurrentTime = DATE_TIME_FORMATTER.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(now)), ZoneId.systemDefault()));

        String formattedSql = formatSql(category, sql);

        return String.format("%s | %d ms | %s | %s",
                formattedCurrentTime, // 포맷된 현재 시간
                elapsed,              // 실행 시간
                category,             // SQL 카테고리
                sql != null ? formattedSql : ""); // SQL 쿼리
    }


    private String formatSql(String category, String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            return sql;
        }

        // DDL과 DML에 따라 SQL 포맷팅 스타일 적용
        if (category.equalsIgnoreCase("statement")) {
            if (sql.trim().toLowerCase().startsWith("create") || sql.trim().toLowerCase().startsWith("alter") || sql.trim().toLowerCase().startsWith("comment")) {
                return FormatStyle.DDL.getFormatter().format(sql);
            } else {
                return FormatStyle.BASIC.getFormatter().format(sql);
            }
        }
        return sql;
    }

}
