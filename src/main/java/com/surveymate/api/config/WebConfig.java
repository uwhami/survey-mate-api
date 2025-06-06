package com.surveymate.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:./uploads/")
                .setCachePeriod(3600); // 캐시 설정 (선택 사항)
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        /* Jackson 컨버터를 우선순위로 설정
         * Contoller에서 return type이 String일때 StringHttpMessageConverter가 우선적용되어 Casting 에러 나는 문제 방지
         * 기존 에러 : ResponseDTO cannot be cast to class java.lang.String
         */

        /* . 'new'로 새로 만드는 대신, 주입받은 objectMapper를 사용하여 컨버터를 생성,
         이렇게 하면 application.yml의 모든 설정이 그대로 적용됨. */
        // converters.add(0, new MappingJackson2HttpMessageConverter());

        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        converters.add(0, jacksonConverter);
    }

}
