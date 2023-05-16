package com.chloneda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 全局跨域处理
 *
 * @author chloneda
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 允许所有路径
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许任何域名使用
        corsConfiguration.addAllowedOrigin("*");
        // 允许跨越发送 Cookie
        corsConfiguration.setAllowCredentials(true);
        // 允许任何请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许任何请求方法（post、get等）跨域调用
        corsConfiguration.addAllowedMethod("*");

        return corsConfiguration;
    }

}
