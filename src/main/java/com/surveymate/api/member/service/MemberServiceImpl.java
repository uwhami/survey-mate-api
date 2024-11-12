package com.surveymate.api.member.service;

import com.surveymate.api.member.dto.MemberLoginDTO;
import com.surveymate.api.member.entity.Member;
import com.surveymate.api.member.repository.MemberRepository;
import com.surveymate.api.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


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

    public Map<String, String> loginMember(MemberLoginDTO memberLoginDTO) {

        try{
            // 사용자 인증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(memberLoginDTO.getUserId(), memberLoginDTO.getPassword())
            );

            // 인증 성공 시 JWT 토큰 생성
            String accessToken = jwtTokenProvider.generateToken(authentication);
            String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

            return Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            );
        }catch(Exception ex){
            throw new RuntimeException("Authentication failed: " + ex.getMessage());
        }

    }
}
