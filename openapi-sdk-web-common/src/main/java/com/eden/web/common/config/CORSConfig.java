package com.eden.web.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域访问配置
 *
 */
@Configuration
public class CORSConfig {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("Content-Type");
        corsConfiguration.addExposedHeader("X-Requested-With");
        corsConfiguration.addExposedHeader("Accept");
        corsConfiguration.addExposedHeader("Origin");
        corsConfiguration.addExposedHeader("Access-Control-Request-Method");
        corsConfiguration.addExposedHeader("Access-Control-Request-Headers");
        corsConfiguration.addExposedHeader("User-Agent");
        corsConfiguration.addExposedHeader("Cache-Control");
        corsConfiguration.addExposedHeader("Date");
        corsConfiguration.addExposedHeader("Server");
        corsConfiguration.addExposedHeader("withCredentials");
        corsConfiguration.addExposedHeader("AccessToken");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

}