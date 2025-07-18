package com.surveymate.api.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surveymate.api.common.dto.ResponseDTO;
import com.surveymate.api.security.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 특정 경로 필터 제외
        String requestUri = request.getRequestURI();
        if (pathMatcher.match("/api/auth/**", requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }


        String token = getJwtFromRequest(request);

        try {
            if (token != null && !token.isEmpty()) {
                jwtTokenProvider.validateToken(token);
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            log.error("JWT 인증 오류: {}", ex.getMessage());
            handleAccessDenied(response, "인증에 실패했습니다.");

        }


    }

    public void handleAccessDenied(HttpServletResponse response, String message) throws IOException {
        ResponseDTO<?> errorResponse = ResponseDTO.failure(message);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
