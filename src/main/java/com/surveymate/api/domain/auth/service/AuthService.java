package com.surveymate.api.domain.auth.service;


import com.surveymate.api.domain.auth.dto.LoginRequest;
import com.surveymate.api.domain.auth.dto.RegisterRequest;
import com.surveymate.api.domain.member.dto.MemberResponseDTO;

import java.util.Map;

public interface AuthService {

    boolean checkDuplicateId(String userId);

    MemberResponseDTO createMember(RegisterRequest registerRequest) throws Exception;

    Map<String, String> loginMember(LoginRequest loginRequest);

    Map<String, String> refreshTokens(String refreshToken);
}
