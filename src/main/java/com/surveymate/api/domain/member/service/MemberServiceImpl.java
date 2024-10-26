package com.surveymate.api.domain.member.service;

import com.surveymate.api.domain.member.dto.MemberDTO;
import com.surveymate.api.domain.member.dto.MemberSignupDTO;
import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.member.mapper.MemberMapper;
import com.surveymate.api.domain.member.repository.MemberRepository;
import com.surveymate.api.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final MemberMapper memberMapper;

    private final CodeGenerator codeGenerator;

    @Transactional
    @Override
    public MemberDTO createMember(MemberSignupDTO memberSignupDTO) throws Exception {
        Member member = memberMapper.toEntity(memberSignupDTO);

        if(isUserIdDuplicate(member.getUserId())){
            throw new Exception("이미 존재하는 ID 입니다.");
        }

        member.setMemNum(codeGenerator.generateCode("MU01"));
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member = memberRepository.save(member);
        return memberMapper.toDTO(member);
    }

    public boolean isUserIdDuplicate(String userId) {
        Optional<Member> duplicate = memberRepository.findByUserId(userId);
        return duplicate.isPresent();
    }
}
