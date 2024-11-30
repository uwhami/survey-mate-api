package com.surveymate.api.domain.auth.controller;

import com.surveymate.api.domain.auth.dto.LoginRequest;
import com.surveymate.api.domain.auth.dto.RegisterRequest;
import com.surveymate.api.domain.auth.service.AuthService;
import com.surveymate.api.domain.member.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/check-duplicate-id")
    public ResponseEntity<Void> checkDuplicateId(@RequestParam String userId) {
        boolean existId = authService.checkDuplicateId(userId);
        if (existId) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }


    @PostMapping("/register")
    public MemberResponseDTO createMember(RegisterRequest registerRequest) throws Exception {
        return authService.createMember(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> memberLogin(LoginRequest loginRequest) {
        try {
            Map<String, String> jwtResponse = authService.loginMember(loginRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshTokens(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            Map<String, String> jwtResponse = authService.refreshTokens(authorizationHeader);
            return ResponseEntity.ok(jwtResponse);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}
