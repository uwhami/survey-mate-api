package com.surveymate.api.member.service;

import com.surveymate.api.member.entity.Member;
import com.surveymate.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;


    @Override
    public boolean checkDuplicateId(String userId) {
        return existsByUserId(userId);
    }

    @Override
    public boolean existsByUserId(String userId) {
        Optional<Member> existId = memberRepository.findByUserId(userId);
        return existId.isPresent();
    }

    @Transactional
    @Override
    public void increasePasswordError(String userId) {
        memberRepository.increasePasswordError(userId);
    }

    @Transactional
    @Override
    public void resetPasswordError(String userId) {
        memberRepository.resetPasswordError(userId);
    }

    @Override
    public int findPasswordErrorByUserId(String userId) {
        return memberRepository.findPasswordErrorByUserId(userId);
    }

}
