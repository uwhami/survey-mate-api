package com.surveymate.api.auth.controller;

import com.surveymate.api.auth.dto.RegisterRequest;
import com.surveymate.api.auth.service.AuthService;
import com.surveymate.api.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public MemberDTO createMember(RegisterRequest registerRequest) throws Exception {
        return authService.createMember(registerRequest);
    }

}
