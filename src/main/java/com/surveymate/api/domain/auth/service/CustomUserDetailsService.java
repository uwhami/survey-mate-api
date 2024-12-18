package com.surveymate.api.domain.auth.service;

import com.surveymate.api.domain.auth.entity.LoginHistory;
import com.surveymate.api.domain.auth.model.CustomUserDetails;
import com.surveymate.api.domain.auth.repository.LoginHistoryRepository;
import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final LoginHistoryRepository loginHistoryRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return CustomUserDetails.builder()
                .userId(member.getUserId())
                .password(member.getPassword())
                .memNum(member.getMemNum())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }


    public CustomUserDetails loadUserByUuid(String uuid) throws UsernameNotFoundException {
        LocalDateTime cutoffTime = LocalDateTime.now().minusDays(1);
        LoginHistory loginInfo = loginHistoryRepository.findByUuid(UUID.fromString(uuid), cutoffTime)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with UUID: " + uuid));

        return CustomUserDetails.builder()
                .memNum(loginInfo.getMemNum())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }

}
