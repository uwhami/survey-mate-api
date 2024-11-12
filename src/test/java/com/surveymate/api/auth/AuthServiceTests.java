package com.surveymate.api.auth;

import com.surveymate.api.auth.dto.RegisterRequest;
import com.surveymate.api.auth.service.AuthService;
import com.surveymate.api.member.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthServiceTests {

    @Autowired
    private AuthService authService;

    @Test
    public void createMember() throws Exception {

        RegisterRequest registerRequest = RegisterRequest.builder()
                .userId("john_doe")
                .password("password123")
                .userName("John Doe")
                .profileImage(null)
                .joinDate("20241111")
                .memStatus("1")
                .build();

        MemberDTO memberDTO = authService.createMember(registerRequest);
        System.out.println(memberDTO.toString());
    }
}
