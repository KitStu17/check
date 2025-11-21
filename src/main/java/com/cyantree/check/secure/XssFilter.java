package com.cyantree.check.secure;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Lucy Xss Servlet Filter 사용 불가에 따른 request -> XssRequestWrapper<request> 처리 필터 구현
// 참조 : https://github.com/dlask913/vue3-spring-project/issues/51
public class XssFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 래퍼 적용
        XssRequestWrapper wrappedRequest = new XssRequestWrapper(request);
        filterChain.doFilter(wrappedRequest, response);
    }

}
