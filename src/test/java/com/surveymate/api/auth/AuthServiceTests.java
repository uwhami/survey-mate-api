package com.surveymate.api.auth;

import com.surveymate.api.domain.auth.dto.LoginRequest;
import com.surveymate.api.domain.auth.dto.RegisterRequest;
import com.surveymate.api.domain.auth.entity.LoginHistory;
import com.surveymate.api.domain.auth.repository.LoginHistoryRepository;
import com.surveymate.api.domain.auth.service.AuthService;
import com.surveymate.api.domain.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class AuthServiceTests {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Test
    public void createMember() throws Exception {

        RegisterRequest registerRequest = RegisterRequest.builder()
                .userId("test121501")
                .password("1234")
                .userName("user121501")
                .userEmail("aaa@aaa.com")
                .profileImage(null)
                .joinDate("20241215")
                .memRole("0")
                .build();

        authService.createMember(registerRequest);
        System.out.println(memberService.existsByUserId("user121501"));
    }

    @Test
    public void login() {
        LoginRequest loginRequest = LoginRequest.builder().userId("test121001").password("1111").build();
        try {
            Map<String, String> jwtResponse = authService.loginMember(loginRequest);
            for (Map.Entry<String, String> entry : jwtResponse.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void loginHistory() {
        UUID uuid = UUID.randomUUID();
        LoginHistory loginHistory = LoginHistory.builder()
                .uuid(uuid)
                .memNum("M202412100001")
                .build();
        System.out.println("uuid : " + uuid);
        loginHistoryRepository.save(loginHistory);

    }
}
