package com.nbc.sociallogin.infrastructure.security.jwt.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbc.sociallogin.infrastructure.security.jwt.filter.JwtAuthenticationFilter;
import com.nbc.sociallogin.infrastructure.security.jwt.filter.JwtExceptionFilter;
import com.nbc.sociallogin.infrastructure.security.jwt.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
	private final JwtUtil jwtUtil;
	private final ObjectMapper objectMapper;

	@Bean
	public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
		FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtAuthenticationFilter(jwtUtil));
		registrationBean.addUrlPatterns("/*"); // 필터를 적용할 URL 패턴을 지정합니다.
		registrationBean.setOrder(2);

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<JwtExceptionFilter> jwtExceptionFilter() {
		FilterRegistrationBean<JwtExceptionFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtExceptionFilter(objectMapper));
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(1);

		return registrationBean;
	}
}
