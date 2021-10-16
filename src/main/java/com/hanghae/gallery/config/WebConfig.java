package com.hanghae.gallery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
        //외부 서버에서 접근요청이 올 때 접근 허가해주는 로직
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            // url 및 method 접근 풀기
            registry.addMapping("/**")
                    .allowedOrigins("http://happyarong.shop.s3-website.ap-northeast-2.amazonaws.com/") // 최종 배포용
                    //.allowedOrigins("http://localhost:3000") // react 서버 허용
                    .allowedMethods(
                            HttpMethod.GET.name(),
                            HttpMethod.POST.name(),
                            HttpMethod.PUT.name(),
                            HttpMethod.DELETE.name(),
                            HttpMethod.HEAD.name())
                    .allowCredentials(true); // 인증관련
        }
}
