package com.surveymate.api.common.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
public class PagedResponse<T> {

    private final List<T> content;
    private final int currentPage;
    private final int totalPages;
    private final long totalElements;
    private final boolean isFirst;
    private final boolean isLast;

    public PagedResponse(Page<T> page) {
        this.content = page.getContent();
        this.currentPage = page.getNumber() + 1; // 0부터 시작하므로 1부터 표시
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.isFirst = page.isFirst();
        this.isLast = page.isLast();
    }

}
