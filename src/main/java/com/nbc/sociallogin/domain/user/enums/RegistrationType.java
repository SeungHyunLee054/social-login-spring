package com.nbc.sociallogin.domain.user.enums;

import com.nbc.sociallogin.infrastructure.oauth.type.OAuthProvider;

public enum RegistrationType {
	DOMAIN,KAKAO;

	public static RegistrationType from(OAuthProvider oAuthProvider) {
		for (RegistrationType registrationType : RegistrationType.values()) {
			if (registrationType.name().equalsIgnoreCase(oAuthProvider.name())) {
				return registrationType;
			}
		}

		return DOMAIN;
	}
}
