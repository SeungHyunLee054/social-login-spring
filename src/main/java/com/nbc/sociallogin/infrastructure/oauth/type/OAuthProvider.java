package com.nbc.sociallogin.infrastructure.oauth.type;

import java.util.Arrays;

import com.nbc.sociallogin.infrastructure.oauth.dto.request.KakaoLoginParam;
import com.nbc.sociallogin.infrastructure.oauth.dto.request.OAuthLoginParam;

public enum OAuthProvider {
	KAKAO {
		@Override
		public OAuthLoginParam getOAuthParam(String authorizationCode) {
			return new KakaoLoginParam(authorizationCode);
		}
	};

	public abstract OAuthLoginParam getOAuthParam(String authorizationCode);

	public static OAuthProvider from(String provider) {
		return Arrays.stream(OAuthProvider.values())
			.filter(p -> p.name().equalsIgnoreCase(provider))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Unknown provider: " + provider));
	}
}
