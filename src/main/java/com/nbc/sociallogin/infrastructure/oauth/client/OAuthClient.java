package com.nbc.sociallogin.infrastructure.oauth.client;

import com.nbc.sociallogin.infrastructure.oauth.dto.request.OAuthLoginParam;
import com.nbc.sociallogin.infrastructure.oauth.dto.response.OAuthUserInfoResponse;
import com.nbc.sociallogin.infrastructure.oauth.type.OAuthProvider;

public interface OAuthClient {
	OAuthProvider oAuthProvider();

	String getToken(OAuthLoginParam loginRequest);

	OAuthUserInfoResponse getUserInfo(String token);

	String getAuthorizationCode();

}
