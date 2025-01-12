package com.surveymate.api.domain.auth.service;

import com.surveymate.api.common.enums.MemberStatus;
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

import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final GuavaCacheService cacheService;

    @Override
    public CustomUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userId, MemberStatus.ACTIVE)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return CustomUserDetails.builder()
                .userId(member.getUserId())
                .password(member.getPassword())
                .memNum(member.getMemNum())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(member.getMemRole().getAuthority())))
                .build();
    }


    public CustomUserDetails loadUserByUuid(String uuid) throws UsernameNotFoundException {

        String memnum = cacheService.getFromCache(uuid);
        if (memnum == null) {
            LoginHistory loginInfo = loginHistoryRepository.findByUuid(UUID.fromString(uuid))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with UUID: " + uuid));
            memnum = loginInfo.getMemNum();
            cacheService.saveToCache(uuid, memnum);
        }

        return CustomUserDetails.builder()
                .memNum(memnum)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }

}
