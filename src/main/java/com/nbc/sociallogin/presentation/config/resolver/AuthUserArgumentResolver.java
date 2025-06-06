package com.nbc.sociallogin.presentation.config.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.nbc.sociallogin.presentation.common.annotation.Auth;
import com.nbc.sociallogin.domain.auth.vo.AuthUser;
import com.nbc.sociallogin.domain.user.enums.UserRole;

import jakarta.servlet.http.HttpServletRequest;

public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasAuthAnnotation = parameter.getParameterAnnotation(Auth.class) != null;
		boolean isAuthUserType = parameter.getParameterType().equals(AuthUser.class);

		if (hasAuthAnnotation != isAuthUserType) {
			throw new RuntimeException("@Auth와 AuthUser 타입은 함께 사용되어야 합니다.");
		}

		return hasAuthAnnotation;
	}

	@Override
	public Object resolveArgument(
		@Nullable MethodParameter parameter,
		@Nullable ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		@Nullable WebDataBinderFactory binderFactory
	) {
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();

		Long userId = (Long)request.getAttribute("userId");
		String email = (String)request.getAttribute("email");
		UserRole userRole = UserRole.from((String)request.getAttribute("userRole"));

		return new AuthUser(userId, email, userRole);
	}
}
