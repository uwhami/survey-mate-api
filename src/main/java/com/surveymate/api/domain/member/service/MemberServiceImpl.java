package com.surveymate.api.domain.member.service;

import com.surveymate.api.domain.member.dto.MemberDTO;
import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.member.mapper.MemberMapper;
import com.surveymate.api.domain.member.repository.MemberRepository;
import com.surveymate.api.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final MemberMapper memberMapper;

    private final CodeGenerator codeGenerator;

    @Transactional
    @Override
    public Member createMember(MemberDTO memberDTO) throws Exception{
        Member member = memberMapper.toEntity(memberDTO);
        member.setMemNum(codeGenerator.generateCode("MU01"));
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }
}
