package com.nbc.sociallogin.infrastructure.oauth.dto.request;

import org.springframework.util.MultiValueMap;

import com.nbc.sociallogin.infrastructure.oauth.type.OAuthProvider;

public interface OAuthLoginParam {

	OAuthProvider oAuthProvider();

	MultiValueMap<String, String> makeBody();
}
