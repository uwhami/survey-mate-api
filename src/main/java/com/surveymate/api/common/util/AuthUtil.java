package com.surveymate.api.common.util;

import com.surveymate.api.domain.auth.model.CustomUserDetails;
import com.surveymate.api.domain.member.exception.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    public static CustomUserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotFoundException();
        }

        return (CustomUserDetails) authentication.getPrincipal();
    }

    public static String getMemNum() {
        return getCurrentUserDetails().getMemNum();
    }

    public static Long getGroupId() {
        return getCurrentUserDetails().getGroupId();
    }

}