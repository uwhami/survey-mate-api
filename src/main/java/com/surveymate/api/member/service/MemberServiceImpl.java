package com.surveymate.api.member.service;

import com.surveymate.api.member.entity.Member;
import com.surveymate.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;


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

    public boolean isUserIdDuplicate(String userId) {
        Optional<Member> duplicate = memberRepository.findByUserId(userId);
        return duplicate.isPresent();
    }


}
