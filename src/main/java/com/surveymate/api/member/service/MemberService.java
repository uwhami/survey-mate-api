package com.surveymate.api.member.service;

import com.surveymate.api.member.dto.MemberDTO;
import com.surveymate.api.member.dto.MemberLoginDTO;
import com.surveymate.api.member.dto.MemberSignupDTO;

import java.util.Map;


public interface MemberService {

    Map<String,String> checkDuplicateId(String userId);

    Map<String, String> loginMember(MemberLoginDTO memberLoginDTO);

    boolean isUserIdDuplicate(String userId);
}
