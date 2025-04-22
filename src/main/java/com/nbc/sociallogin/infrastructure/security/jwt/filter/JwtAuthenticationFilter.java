package com.nbc.sociallogin.infrastructure.security.jwt.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.nbc.sociallogin.infrastructure.security.jwt.util.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String uri = request.getRequestURI();

		if (uri.startsWith("/swagger") || uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger-resources")) {
			filterChain.doFilter(request, response);
			return;
		}

		if (uri.startsWith("/auth") || uri.startsWith("/oauth")|| uri.contains("html")) {
			filterChain.doFilter(request, response);
			return;
		}

		String header = request.getHeader("Authorization");
		if (header == null || header.isBlank()) {
			throw new RuntimeException("Authorization header is empty");
		}

		String token = resolveToken(header);
		if (jwtUtil.isTokenExpired(token)) {
			throw new RuntimeException("Token expired");
		}

		Claims claims = jwtUtil.parseToken(token);

		request.setAttribute("email", claims.getSubject());
		request.setAttribute("userId", Long.parseLong(claims.getId()));
		request.setAttribute("userRole", claims.get("userRole"));

		filterChain.doFilter(request, response);
	}

	private String resolveToken(String authorization) {
		if (!authorization.startsWith("Bearer ")) {
			throw new RuntimeException("Authorization header is incorrect");
		}

		return authorization.substring("Bearer ".length());
	}
}
