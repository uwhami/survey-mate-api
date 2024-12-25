package com.surveymate.api.security.filter;

import com.surveymate.api.domain.auth.model.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MemnumInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            String memNum = userDetails.getMemNum();

            if (memNum != null) {
                request.setAttribute("memnum", memNum);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return false;
            }
        }
        return true;
    }

}
