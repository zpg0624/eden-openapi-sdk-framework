package com.eden.web.common.config;

import com.eden.web.common.interceptor.SecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* 
* web自定义配置 
* @author eden 【】
* @since 2019/10/28
*/
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/pages/**")
                .excludePathPatterns("/error");
    }
}
