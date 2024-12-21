package com.surveymate.api.aspect;

import com.surveymate.api.common.dto.MemnumAware;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class MemnumAspect {

    // 모든 컨트롤러 메서드에 적용
    @Before("execution(* com.surveymate.api..*Controller.*(..)) && args(dto,..)")
    public void injectMemnum(JoinPoint joinPoint, Object dto) {
        // DTO가 MemnumAware를 구현했는지 확인
        if (dto instanceof MemnumAware) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String memnum = (String) request.getAttribute("memnum");

            // DTO에 memnum 값 설정
            if (memnum != null) {
                ((MemnumAware) dto).setMemNum(memnum);
            }
        }
    }

}
