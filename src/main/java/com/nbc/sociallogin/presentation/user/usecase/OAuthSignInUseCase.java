package com.nbc.sociallogin.presentation.user.usecase;

import org.springframework.stereotype.Component;

import com.nbc.sociallogin.domain.auth.vo.AuthUser;
import com.nbc.sociallogin.domain.user.dto.UserInfo;
import com.nbc.sociallogin.domain.user.entity.User;
import com.nbc.sociallogin.domain.user.service.UserService;
import com.nbc.sociallogin.infrastructure.oauth.dto.request.OAuthLoginParam;
import com.nbc.sociallogin.infrastructure.oauth.dto.response.OAuthUserInfoResponse;
import com.nbc.sociallogin.infrastructure.oauth.provider.OAuthClientProvider;
import com.nbc.sociallogin.infrastructure.oauth.type.OAuthProvider;
import com.nbc.sociallogin.infrastructure.security.util.JwtUtil;
import com.nbc.sociallogin.presentation.auth.dto.OAuthSignupDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthSignInUseCase {
	private final UserService userService;
	private final OAuthClientProvider oAuthClientProvider;
	private final JwtUtil jwtUtil;

	public String signIn(OAuthSignupDto signupDto) {
		OAuthLoginParam oAuthLoginParam = convertToLoginParam(signupDto);
		OAuthUserInfoResponse userInfoResponse = oAuthClientProvider.fetchUserInfo(oAuthLoginParam);

		User user = userService.findOrCreate(UserInfo.convertToUserInfo(userInfoResponse));

		return jwtUtil.generateToken(new AuthUser(user.getId(), user.getEmail(), user.getUserRole()));
	}

	public String getAuthorizationCode(OAuthProvider oAuthProvider) {
		return oAuthClientProvider.getAuthorizationCode(oAuthProvider);
	}

	private OAuthLoginParam convertToLoginParam(OAuthSignupDto signupDto) {
		OAuthProvider oAuthProvider = OAuthProvider.from(signupDto.getProvider());

		return oAuthProvider.getOAuthParam(signupDto.getAuthorizationCode());
	}

}
