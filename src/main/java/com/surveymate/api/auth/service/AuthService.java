package com.surveymate.api.auth.service;


import com.surveymate.api.auth.dto.RegisterRequest;
import com.surveymate.api.member.dto.MemberDTO;

public interface AuthService {

    MemberDTO createMember(RegisterRequest memberSignupDTO) throws Exception;
}
