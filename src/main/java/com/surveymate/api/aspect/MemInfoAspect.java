package com.surveymate.api.aspect;

import com.surveymate.api.common.dto.MemInfoAware;
import com.surveymate.api.domain.auth.model.CustomUserDetails;
import com.surveymate.api.domain.member.exception.UserNotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MemInfoAspect {

    // 모든 컨트롤러 메서드에 적용
    @Before("execution(* com.surveymate.api..*Controller.*(..)) && !execution(* com.surveymate.api.domain.auth..*(..)) && args(..)")
    public void injectMemInfo(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotFoundException();
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        for (Object arg : args) {
            if (arg instanceof MemInfoAware) {
                String memNum = userDetails.getMemNum();
                Long groupId = userDetails.getGroupId();

                if (memNum != null) {
                    ((MemInfoAware) arg).setMemNum(memNum);
                    ((MemInfoAware) arg).setGroupId(groupId);
                }
            }
        }
    }

}
