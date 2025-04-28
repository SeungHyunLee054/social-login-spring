package com.nbc.sociallogin.domain.user.enums;

import java.util.Arrays;

import com.nbc.sociallogin.infrastructure.oauth.type.OAuthProvider;

public enum RegistrationType {
	DOMAIN, KAKAO;

	public static RegistrationType from(OAuthProvider oAuthProvider) {
		return Arrays.stream(RegistrationType.values())
			.filter(r -> r.name().equalsIgnoreCase(oAuthProvider.name()))
			.findFirst()
			.orElse(DOMAIN);
	}
}
