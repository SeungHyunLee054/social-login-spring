package com.nbc.sociallogin.application.auth.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nbc.sociallogin.infrastructure.oauth.client.OAuthClient;
import com.nbc.sociallogin.infrastructure.oauth.dto.request.OAuthLoginParam;
import com.nbc.sociallogin.infrastructure.oauth.dto.response.OAuthUserInfoResponse;
import com.nbc.sociallogin.infrastructure.oauth.type.OAuthProvider;

@Service
public class OAuthClientService {
	private final Map<OAuthProvider, OAuthClient> authClients;

	public OAuthClientService(List<OAuthClient> authClients) {
		this.authClients = authClients.stream().collect(Collectors
			.toUnmodifiableMap(OAuthClient::oAuthProvider,
				Function.identity()));
	}

	public OAuthUserInfoResponse fetchUserInfo(OAuthLoginParam loginParam) {
		OAuthClient oAuthClient = authClients.get(loginParam.oAuthProvider());
		String accessToken = oAuthClient.getToken(loginParam);

		return oAuthClient.getUserInfo(accessToken);
	}

	public String getAuthorizationCode(OAuthProvider oAuthProvider) {
		OAuthClient oAuthClient = authClients.get(oAuthProvider);

		return oAuthClient.getAuthorizationCode();
	}
}
