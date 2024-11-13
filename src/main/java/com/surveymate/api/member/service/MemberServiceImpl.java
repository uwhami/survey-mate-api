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
    public Map<String, String> checkDuplicateId(String userId) {
        boolean existId = existsByUserId(userId);
        Map<String, String> param = new HashMap<>();
        if(existId){
            param.put("status", "FAILURE");
            param.put("errorCode", "USERID_TAKEN");
        }else{
            param.put("status", "SUCCESS");
        }
        return param;
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
}
