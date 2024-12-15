package com.surveymate.api.config;

import com.surveymate.api.security.filter.MemnumInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final MemnumInterceptor memnumInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:./uploads/")
                .setCachePeriod(3600); // 캐시 설정 (선택 사항)
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(memnumInterceptor)
                .addPathPatterns("/**") // 모든 경로에 대해 인터셉터 실행
                .excludePathPatterns("/api/auth/**"); // 인증 예외 경로 설정
    }

}
