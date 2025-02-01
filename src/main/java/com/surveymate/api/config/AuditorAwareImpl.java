package com.surveymate.api.config;

import com.surveymate.api.domain.auth.model.CustomUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Log4j2
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(null == authentication || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        //사용자 환경에 맞게 로그인한 사용자의 정보를 불러온다.
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

        return Optional.ofNullable(userDetails.getMemNum());
    }
}
