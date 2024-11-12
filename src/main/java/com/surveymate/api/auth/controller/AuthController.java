package com.surveymate.api.auth.controller;

import com.surveymate.api.auth.dto.LoginRequest;
import com.surveymate.api.auth.dto.RegisterRequest;
import com.surveymate.api.auth.service.AuthService;
import com.surveymate.api.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public MemberDTO createMember(RegisterRequest registerRequest) throws Exception {
        return authService.createMember(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> memberLogin(LoginRequest loginRequest) throws Exception {
        try {
            Map<String, String> jwtResponse = authService.loginMember(loginRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}
