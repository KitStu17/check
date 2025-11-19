package com.cyantree.check.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.httpBasic(basic -> basic.disable());
        // 기본 CSP 설정
        // 자기자신에 대해서 리소스 접근이 가능하도록 허용
        // 자기 자신에 대해 <frame>, <iframe>, <object>, <embed> 태그 사용 가능하도록 허용
        // HTML 내 인라인 스크립트 허용(테스트를 위해 허용, lucy 필터 사용 필요)
        // 추후 .properties 파일로 이동 필요
        http.headers(headers -> headers.contentSecurityPolicy(customizer -> 
            customizer.policyDirectives("default-src 'self'; frame-ancestors 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline';")
        ));
        // 세션 사용 X
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(auth -> {
            try {
                auth.requestMatchers("/**").permitAll();
            } catch (Exception e) {
                log.error("Init Error : ", e);
            }
        });

        return http.build();
    }

    // 테스트를 위해 모든 도메인 허용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final long MAX_AGE_SECS = 3600;
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        config.setMaxAge(MAX_AGE_SECS);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
