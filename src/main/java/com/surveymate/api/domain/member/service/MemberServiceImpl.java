package com.surveymate.api.domain.member.service;

import com.surveymate.api.domain.member.dto.MemberDTO;
import com.surveymate.api.domain.member.dto.MemberSignupDTO;
import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.member.mapper.MemberMapper;
import com.surveymate.api.domain.member.repository.MemberRepository;
import com.surveymate.api.exception.UserAlreadyExistsException;
import com.surveymate.api.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final MemberMapper memberMapper;

    private final CodeGenerator codeGenerator;

    @Override
    public Map<String, String> checkDuplicateId(String userId) {
        boolean existId = isUserIdDuplicate(userId);
        Map<String, String> param = new HashMap<>();
        if(existId){
            param.put("status", "FAILURE");
            param.put("errorCode", "USERID_TAKEN");
        }else{
            param.put("status", "SUCCESS");
        }
        return param;
    }

    @Transactional
    @Override
    public MemberDTO createMember(MemberSignupDTO memberSignupDTO) {
        Member member = memberMapper.toEntity(memberSignupDTO);

        if(isUserIdDuplicate(member.getUserId())){
            throw new UserAlreadyExistsException("이미 존재하는 ID 입니다. : " + member.getUserId());
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
