package com.cyantree.check.config;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cyantree.check.secure.HTMLCharacterEscapes;
import com.cyantree.check.secure.XssFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jsonEscapeConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
    }

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilter() {
        FilterRegistrationBean<XssFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new XssFilter());
        registrationBean.addUrlPatterns("/api/**");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
        // response에 대한 XSS 처리
        ObjectMapper copy = objectMapper.copy();
        copy.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
        return new MappingJackson2HttpMessageConverter(copy);
    }
}
