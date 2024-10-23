package com.surveymate.api.domain.member.service;

import com.surveymate.api.domain.member.dto.MemberDTO;
import com.surveymate.api.domain.member.entity.Member;


public interface MemberService {

    Member createMember(MemberDTO memberDTO) throws Exception;


}
