package com.surveymate.api.domain.member.service;

import com.surveymate.api.domain.member.dto.MemberDTO;
import com.surveymate.api.domain.member.dto.MemberSignupDTO;


public interface MemberService {

    MemberDTO createMember(MemberSignupDTO memberSignupDTO) throws Exception ;


}
