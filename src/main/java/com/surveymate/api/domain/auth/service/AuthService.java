package com.surveymate.api.domain.auth.service;


import com.surveymate.api.common.enums.SocialType;
import com.surveymate.api.domain.auth.dto.LoginRequest;
import com.surveymate.api.domain.auth.dto.PasswordResetRequest;
import com.surveymate.api.domain.auth.dto.RegisterRequest;
import com.surveymate.api.domain.member.entity.Member;

import java.util.Map;
import java.util.UUID;

public interface AuthService {

    boolean checkDuplicateId(String userId);

    String sendVerificationCode(String email);

    Map<String,String> socialLoginOrRegisterUser(Map<String,Object> userInfo, SocialType socialType) throws Exception;

    void createMember(RegisterRequest registerRequest) throws Exception;

    void createMember(RegisterRequest registerRequest, SocialType socialType) throws Exception;

    Map<String, String> loginMember(LoginRequest loginRequest);

    Map<String, String> loginMember(LoginRequest loginRequest, int social);

    Map<String, String> refreshTokens(String refreshToken);

    String findUserIdByUSerEmail(String email);

    void passwordReset(PasswordResetRequest request);

    Member findMemberByLoginHistoryUuid(UUID uuid);
}
