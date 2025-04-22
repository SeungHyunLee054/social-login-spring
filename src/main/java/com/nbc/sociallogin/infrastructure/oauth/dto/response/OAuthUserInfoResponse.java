package com.nbc.sociallogin.infrastructure.oauth.dto.response;

import java.time.LocalDate;

import com.nbc.sociallogin.infrastructure.oauth.type.OAuthProvider;

public interface OAuthUserInfoResponse {
	String getEmail();

	String getName();

	LocalDate getBirth();

	String getPhone();

	OAuthProvider getOAuthProvider();
}
