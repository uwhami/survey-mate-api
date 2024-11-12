package com.surveymate.api.auth.service;


import com.surveymate.api.auth.dto.LoginRequest;
import com.surveymate.api.auth.dto.RegisterRequest;
import com.surveymate.api.member.dto.MemberDTO;

import java.util.Map;

public interface AuthService {

    MemberDTO createMember(RegisterRequest registerRequest) throws Exception;

    Map<String, String> loginMember(LoginRequest loginRequest) throws Exception;

}
