package com.surveymate.api.domain.member.service;

import com.surveymate.api.domain.member.dto.MemberDTO;
import com.surveymate.api.domain.member.dto.MemberSignupDTO;

import java.util.Map;


public interface MemberService {

    Map<String,String> checkDuplicateId(String userId);
    MemberDTO createMember(MemberSignupDTO memberSignupDTO) throws Exception;


}
