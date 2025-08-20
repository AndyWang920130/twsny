package com.example.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RemoveDpopFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 包装 request，屏蔽 dpop 参数
        HttpServletRequest wrappedRequest = new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
                if ("DPoP".equalsIgnoreCase(name)) {
                    return null; // 强制移除
                }
                return super.getHeader(name);
            }

            @Override
            public String getParameter(String name) {
                if ("dpop".equalsIgnoreCase(name)) {
                    return null; // 强制移除
                }
                return super.getParameter(name);
            }
        };

        filterChain.doFilter(wrappedRequest, response);
    }
}