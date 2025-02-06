package com.surveymate.api.domain.auth.controller;

import com.surveymate.api.domain.auth.dto.LoginRequest;
import com.surveymate.api.domain.auth.dto.PasswordResetRequest;
import com.surveymate.api.domain.auth.dto.RegisterRequest;
import com.surveymate.api.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/check-duplicate-id")
    public boolean checkDuplicateId(@RequestParam String userId) {
        return authService.checkDuplicateId(userId);
    }

    @GetMapping("/send-verification-code")
    public String sendVerificationCode(@RequestParam String email) throws Exception{
        return authService.sendVerificationCode(email);
    }


    @PostMapping("/register")
    public void createMember(RegisterRequest registerRequest) throws Exception {
        authService.createMember(registerRequest);
    }

    @PostMapping("/login")
    public Map<String, String> memberLogin(LoginRequest loginRequest) {
        return authService.loginMember(loginRequest);
    }

    @PostMapping("/refresh")
    public Map<String, String> refreshTokens(@RequestHeader("Authorization") String authorizationHeader) {
        return authService.refreshTokens(authorizationHeader);
    }

    @GetMapping("/findIdByEmail")
    public String findIdByEmail(@RequestParam String email) {
        return authService.findUserIdByUSerEmail(email);
    }

    @PostMapping("/resetPassword")
    public void resetPassword(@RequestBody PasswordResetRequest request) {
        authService.passwordReset(request);
    }

}
