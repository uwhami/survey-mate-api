package com.surveymate.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TransactionIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // UUID 생성 후 하이픈 제거
        String rawTransactionId = UUID.randomUUID().toString();
        String cleanTransactionId = rawTransactionId.replaceAll("-", "");

        // log4j2의 MDC: ThreadContext
        ThreadContext.put("transactionId", cleanTransactionId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            ThreadContext.remove("transactionId");
        }
    }
}
