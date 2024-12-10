package com.surveymate.api.auth;

import com.surveymate.api.domain.auth.dto.LoginRequest;
import com.surveymate.api.domain.auth.dto.RegisterRequest;
import com.surveymate.api.domain.auth.service.AuthService;
import com.surveymate.api.domain.member.dto.MemberResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class AuthServiceTests {

    @Autowired
    private AuthService authService;

    @Test
    public void createMember() throws Exception {

        RegisterRequest registerRequest = RegisterRequest.builder()
                .userId("test121001")
                .password("1234")
                .userName("user121001")
                .userEmail("aaa@aaa.com")
                .profileImage(null)
                .joinDate("20241210")
                .memStatus("1")
                .build();

        MemberResponseDTO memberResponseDTO = authService.createMember(registerRequest);
        System.out.println(memberResponseDTO.toString());
    }

    @Test
    public void login() {
        LoginRequest loginRequest = LoginRequest.builder().userId("test121001").password("1121").build();
        try{
            Map<String, String> jwtResponse = authService.loginMember(loginRequest);
            for(Map.Entry<String, String> entry : jwtResponse.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
