package com.nbc.sociallogin.infrastructure.oauth.dto.request;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.nbc.sociallogin.infrastructure.oauth.type.OAuthProvider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoLoginParam implements OAuthLoginParam {
	private final String authorizationCode;

	@Override
	public OAuthProvider oAuthProvider() {
		return OAuthProvider.KAKAO;
	}

	@Override
	public MultiValueMap<String, String> makeBody() {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("code", authorizationCode);

		return body;
	}
}
