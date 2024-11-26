package com.surveymate.api.auth;

import com.surveymate.api.domain.auth.dto.RegisterRequest;
import com.surveymate.api.domain.auth.service.AuthService;
import com.surveymate.api.domain.member.dto.MemberResponseDTO;
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
                .userId("test2")
                .password("password123")
                .userName("John Doe")
                .profileImage(null)
                .joinDate("20241111")
                .memStatus("1")
                .build();

        MemberResponseDTO memberResponseDTO = authService.createMember(registerRequest);
        System.out.println(memberResponseDTO.toString());
    }
}
