package com.surveymate.api.domain.auth.controller;


import com.surveymate.api.common.enums.SocialType;
import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/oauth/google")
public class GoogleController {

    private final WebClient webClient = WebClient.create(); // WebClient 인스턴스 생성

    private static final String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";
    private final AuthService authService;


    @PostMapping
    public Map<String, String> verifyToken(@RequestHeader("Authorization") String accessToken) {
        try {

            if (accessToken.startsWith("Bearer ")) {
                accessToken = accessToken.substring(7);
            }

            // Google API 호출
            @SuppressWarnings("unchecked")
            Map<String, Object> userInfo = webClient.get()
                    .uri(GOOGLE_USERINFO_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block(); // 블로킹 방식으로 처리 (필요 시 비동기로 변경 가능)



            return authService.socialLoginOrRegisterUser(userInfo, SocialType.GOOGLE);

        } catch (Exception e) {
            throw new CustomRuntimeException(e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token verification failed.");
        }
    }

}
