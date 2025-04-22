package com.nbc.sociallogin.application.auth.usecase;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbc.sociallogin.application.auth.service.OAuthClientService;
import com.nbc.sociallogin.domain.auth.vo.AuthUser;
import com.nbc.sociallogin.domain.user.entity.User;
import com.nbc.sociallogin.domain.user.service.UserService;
import com.nbc.sociallogin.infrastructure.oauth.dto.request.OAuthLoginParam;
import com.nbc.sociallogin.infrastructure.oauth.dto.response.OAuthUserInfoResponse;
import com.nbc.sociallogin.infrastructure.oauth.type.OAuthProvider;
import com.nbc.sociallogin.infrastructure.security.jwt.util.JwtUtil;
import com.nbc.sociallogin.presentation.auth.dto.OAuthSignupDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthSignInUseCase {
	private final UserService userService;
	private final OAuthClientService oAuthClientService;
	private final JwtUtil jwtUtil;

	@Transactional
	public String signIn(OAuthSignupDto signupDto) {
		OAuthLoginParam oAuthLoginParam = convertToLoginParam(signupDto);
		OAuthUserInfoResponse userInfoResponse = oAuthClientService.fetchUserInfo(oAuthLoginParam);

		User user = userService.findOrCreate(userInfoResponse);

		return jwtUtil.generateToken(new AuthUser(user.getId(), user.getEmail(), user.getUserRole()));
	}

	public String getAuthorizationCode(OAuthProvider oAuthProvider) {
		return oAuthClientService.getAuthorizationCode(oAuthProvider);
	}

	private OAuthLoginParam convertToLoginParam(OAuthSignupDto signupDto) {
		OAuthProvider oAuthProvider = OAuthProvider.from(signupDto.getProvider());

		return oAuthProvider.getOAuthParam(signupDto.getAuthorizationCode());
	}

}
